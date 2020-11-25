package io.github.bloeckchengrafik.contractbook.commands;

import io.github.bloeckchengrafik.contractbook.Cryptbook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.material.MaterialData;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Contract implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player) || strings.length <= 2) return false;
        StringBuilder cont = new StringBuilder();
        for (String string : strings) {
            cont.append(string);
        }

        Player player1 = (Player) commandSender;
        Player player2;

        try {
            player2 = Bukkit.getPlayer(strings[0]);
        }catch (Exception ignored){
            player1.sendMessage("§cDer Spieler wurde nicht gefunden.");
            return true;
        }

        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bm = (BookMeta) itemStack.getData();
        bm.setAuthor("Official Book by Server");
        bm.setGeneration(BookMeta.Generation.ORIGINAL);
        bm.setTitle("Vertrag #" + UUID.randomUUID().toString());

        bm.setPages(cont.toString());

        String sha1;
        String sha2;

        try {
            sha1 = Cryptbook.SHA512(bm.getPages().get(0) + player1.getUniqueId().toString());
            sha2 = Cryptbook.SHA512(bm.getPages().get(0) + player2.getUniqueId().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

        bm.setPages(cont.toString(), sha1, sha2);
        itemStack.setData((MaterialData) bm);

        if(player1.getInventory().firstEmpty() == -1){
            player1.sendMessage("§cDu hast keinen Platz im Inventar.");
        }

        if(player2.getInventory().firstEmpty() == -1){
            player1.sendMessage("§c" + player2.getDisplayName() + " hat keinen Platz im Inventar.");
        }

        player1.getInventory().addItem(itemStack.clone());
        player2.getInventory().addItem(itemStack.clone());

        return true;
    }
}
