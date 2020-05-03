package halacraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICorePlugin {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args);
    public void onEnable();
    public void onDisable();
}