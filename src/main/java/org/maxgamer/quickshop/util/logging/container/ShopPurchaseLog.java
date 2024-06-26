/*
 * This file is a part of project QuickShop, the name is ShopPurchaseLog.java
 *  Copyright (C) PotatoCraft Studio and contributors
 *
 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.maxgamer.quickshop.util.logging.container;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maxgamer.quickshop.api.shop.ShopInfoStorage;
import org.maxgamer.quickshop.api.shop.ShopType;

import java.util.UUID;

@AllArgsConstructor
@Data
public class ShopPurchaseLog implements ReadableLog {
    private static int v = 1;
    private ShopInfoStorage shop;
    private ShopType type;
    private UUID trader;
    private String itemName;
    private String itemStack;
    private int amount;
    private double balance;
    private double tax;

    @Override
    public String toReadableLog() {
        return trader + " trade with shop at " + shop.getPosition() + " for " + amount + "x " + itemStack + "(" + itemName + ") with balance " + balance + ", tax: " + tax + ", shop data:" + shop.toJson();
    }
}
