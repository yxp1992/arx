/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2016 Fabian Prasser, Florian Kohlmayer and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deidentifier.arx.risk.msu;

import java.util.HashSet;
import java.util.Set;

/**
 * Each itemset is a concrete value for a concrete attribute
 * 
 * @author Fabian Prasser
 */
public class SUDA2Item {

    /**
     * Packs column and value into a long to be used as a key
     * @param column
     * @param value
     * @return
     */
    public static long getId(int column, int value) {
        return ((long)column) << 32 | ((long)value) & 0xFFFFFFFFL;
    }

    /** Column */
    private final int    column;
    /** Unique id */
    private final long   id;
    /** Value */
    private final int    value;
    /** Hash code */
    private final int    hashCode;
    /** Support rows */
    private Set<Integer> rows = new HashSet<>();

    /**
     * Creates a new item
     * @param column
     * @param value
     */
    public SUDA2Item(int column, int value) {
        this.column = column;
        this.value = value;
        this.hashCode = (31 + column) * 31 + value;
        this.id = getId(column, value);
    }

    /**
     * Adds a row
     * @param row
     */
    public void addRow(int row) {
        this.rows.add(row);
    }

    @Override
    public boolean equals(Object obj) {
        SUDA2Item other = (SUDA2Item) obj;
        if (column != other.column || value != other.value) return false;
        return true;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Returns the id
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the rows in which this item is located
     * @return
     */
    public Set<Integer> getRows() {
        return this.rows;
    }

    /**
     * Returns the support
     * @return
     */
    public int getSupport() {
        return this.rows.size();
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Returns whether the item is contained in a given row
     * @param row
     * @return
     */
    public boolean isContained(int[] row) {
        return row[column] == value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(column).append(",").append(value).append(")");
        return builder.toString();
    }

    /**
     * Adds all rows to the item
     * @param rows
     */
    public void addRows(Set<Integer> rows) {
        this.rows.addAll(rows);
    }
}