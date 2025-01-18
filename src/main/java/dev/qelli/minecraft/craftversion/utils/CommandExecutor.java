package dev.qelli.minecraft.craftversion.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CommandExecutor {

    public static ProcessResult git(String gitPath, File directory, String... args) {


        String[] command = new String[args.length + 1];
        command[0] = gitPath;
        System.arraycopy(args, 0, command, 1, args.length);

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        processBuilder.directory(directory);
        processBuilder.redirectErrorStream(true);
        // TODO: Find a way to prevent input requests from the process
        // processBuilder.redirectInput(ProcessBuilder.Redirect.from(new File("/dev/null")));

        int exitCode = -1;

        try {
            Process process = processBuilder.start();
            process.getOutputStream().close();
            exitCode = process.waitFor();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining("\n"));
                return new ProcessResult(exitCode, output, null);
            } catch(Exception e) {
                e.printStackTrace();
                return new ProcessResult(exitCode, null, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ProcessResult(exitCode, null, e.getMessage());
        }
    }

}
