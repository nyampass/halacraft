package halacraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class Plugin extends JavaPlugin {
    static {
        Thread.currentThread().setContextClassLoader(Plugin.class.getClassLoader());

        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("halacraft.core"));
    }

    private static final IFn cljCorePlugin = Clojure.var("halacraft.core", "->CorePlugin");
    private ICorePlugin corePlugin;

    @Override
    public void onEnable() {
        corePlugin = (ICorePlugin)cljCorePlugin.invoke();
        corePlugin.onEnable();
    }

    @Override
    public void onDisable() {
        corePlugin.onDisable();
        corePlugin = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return corePlugin.onCommand(sender, command, label, args);
    }
}
