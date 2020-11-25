package io.github.bloeckchengrafik.contractbook;

import io.github.bloeckchengrafik.contractbook.commands.Contract;
import io.github.bloeckchengrafik.contractbook.commands.Sha512_vf;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Cryptbook extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.broadcastMessage("§aWelcome to Cryptbook by Bloeckchengrafik");
        getCommand("contract").setExecutor(new Contract());
        getCommand("sha512").setExecutor(new Sha512_vf());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private static String convertToHex(byte[] data)
    {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            }
            while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA512(String text)
            throws NoSuchAlgorithmException
    {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        byte[] sha1hash;
        md.update(text.getBytes(StandardCharsets.UTF_8), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}
