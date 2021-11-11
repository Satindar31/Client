package me.mcblueparrot.client.tweak;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class Tweaker implements ITweaker {

    public static boolean optifine;
    private static List<String> args = new ArrayList<>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        try {
            Class.forName("optifine.Patcher");
            optifine = true;
        }
        catch(ClassNotFoundException error) {
            Tweaker.args.addAll(args);
            if(gameDir != null) {
                Tweaker.args.add("--gameDir");
                Tweaker.args.add(gameDir.getAbsolutePath());
            }
            if(assetsDir != null) {
                Tweaker.args.add("--assetsDir");
                Tweaker.args.add(assetsDir.getAbsolutePath());
            }
            if(profile != null) {
                Tweaker.args.add("--version");
                Tweaker.args.add(profile);
            }
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        System.out.println("injecting");
        classLoader.registerTransformer("me.mcblueparrot.client.tweak.transformer.ClassTransformer");

        MixinBootstrap.init();

        Mixins.addConfiguration("mixins.solclient.json");

        // Replay Mod
        Mixins.addConfiguration("mixins.core.replaymod.json");
        Mixins.addConfiguration("mixins.recording.replaymod.json");
        Mixins.addConfiguration("mixins.render.replaymod.json");
        Mixins.addConfiguration("mixins.render.blend.replaymod.json");
        Mixins.addConfiguration("mixins.replay.replaymod.json");
        // Mixins.addConfiguration("mixins.compat.mapwriter.replaymod.json");
        if(optifine) Mixins.addConfiguration("mixins.compat.shaders.replaymod.json");
        Mixins.addConfiguration("mixins.extras.playeroverview.replaymod.json");
        Mixins.addConfiguration("mixins.jgui.json");
        
        MixinEnvironment env = MixinEnvironment.getDefaultEnvironment();

        if(env.getObfuscationContext() == null) {
            env.setObfuscationContext("notch");
        }

        env.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }

}
