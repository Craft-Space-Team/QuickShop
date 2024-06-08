package org.maxgamer.quickshop;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.api.shop.Shop;
import org.maxgamer.quickshop.util.MsgUtil;
import org.maxgamer.quickshop.util.PlayerFinder;

public class Placeholder extends PlaceholderExpansion {

    private final QuickShop plugin; //

    public Placeholder(QuickShop plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "shop";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        if (!offlinePlayer.isOnline()) {
            return null;
        }

        Player player = offlinePlayer.getPlayer();
        if (player == null) {
            return null;
        }

        BlockIterator bIt = new BlockIterator(player, 10);

        while (bIt.hasNext()) {
            final Block b = bIt.next();
            final Shop shop = plugin.getShopManager().getShop(b.getLocation());

            if (shop == null) {
                continue;
            }

            if (params.equalsIgnoreCase("price")) {
                return String.format("%.2f", shop.getPrice());
            }

            if (params.equalsIgnoreCase("type")) {
                return (shop.isSelling() ?
                        plugin.text().of("menu.this-shop-is-selling").forLocale() :
                        plugin.text().of("menu.this-shop-is-buying").forLocale());
            }

            if (params.equalsIgnoreCase("itemname")) {
                return MsgUtil.getTranslateText(shop.getItem());
            }

            if (params.equalsIgnoreCase("itemnumber")) {
                return (shop.getRemainingStock() == -1 ? plugin.text().of("signs.unlimited").forLocale() : String.valueOf(shop.getRemainingStock()));
            }

            if (params.equalsIgnoreCase("owner")) {
                return PlayerFinder.findNameByUUID(shop.getOwner());
            }

            if (params.equalsIgnoreCase("staffnumber")) {
                return String.valueOf(shop.getStaffs().size());
            }

            for (int i = 0; i < plugin.getConfig().getInt("shop.max-staffs"); i++) {
                if (params.equalsIgnoreCase("staff_" + (i + 1))) {
                    if (i < shop.getStaffs().size()) {
                        return PlayerFinder.findNameByUUID(shop.getStaffs().get(i));
                    } else {
                        return "null";
                    }
                }
            }

        }
        return null;

    }

}
