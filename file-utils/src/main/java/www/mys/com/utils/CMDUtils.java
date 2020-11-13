package www.mys.com.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CMDUtils {

    private static final Logger log = Logger.getLogger(CMDUtils.class.getName());

    private static boolean mHaveRoot = false;

    public static boolean haveRoot() {
        if (!mHaveRoot) {
            mHaveRoot = execRootCmdSilent("echo test") != -1;
        }
        return mHaveRoot;
    }

    public static String runRoot(String cmd) {
        StringBuilder stringBuilder = new StringBuilder();
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line;
            while ((line = dis.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            p.waitFor();
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
        } finally {
            CloseUtils.closeSilently(dos);
            CloseUtils.closeSilently(dis);
        }
        log.log(Level.WARNING, "run root cmd=" + cmd + "\n" + stringBuilder);
        return stringBuilder.toString();
    }

    private static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
        } finally {
            CloseUtils.closeSilently(dos);
        }
        return result;
    }

    public static String run(final String str) {
        return run(str.split(" "));
    }

    public static String run(final String[] str) {
        StringBuilder result = new StringBuilder();
        run(new ArrayList<String[]>() {{
            add(str);
        }}, new RunBack() {
            private int messageCount, errorCount;

            @Override
            public void onStart(String cmd) {
            }

            @Override
            public void onMessage(String message) {
                result.append(message);
                if (messageCount++ > 0) {
                    result.append("\n");
                }
            }

            @Override
            public void onError(String message) {
                result.append(message);
                if (errorCount++ > 0) {
                    result.append("\n");
                }
            }

            @Override
            public void onEnd(String cmd, int exitCode) {
            }
        });
        return result.toString();
    }

    public static void run(final String str, RunBack runBack) {
        run(str.split(" "), runBack);
    }

    public static void run(final String[] str, RunBack runBack) {
        run(new ArrayList<String[]>() {{
            add(str);
        }}, runBack);
    }

    public static void run(List<String[]> strs, RunBack runBack) {
        if (strs != null && strs.size() > 0) {
            Runtime runtime = Runtime.getRuntime();
            for (String[] str : strs) {
                commonRun(runtime, str, runBack);
            }
        }
    }

    private static int commonRun(Runtime runtime, String[] strs, RunBack runBack) {
        StringBuilder cmdStr = new StringBuilder();
        for (String str : strs) {
            cmdStr.append(str).append(" ");
        }
        runBack.onStart(cmdStr.toString());
        Process process;
        try {
            process = runtime.exec(strs);
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
            runBack.onEnd(cmdStr.toString(), -1);
            return -1;
        }
        try {
            process.waitFor();
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
            runBack.onEnd(cmdStr.toString(), -1);
            return -1;
        }
        int resultCode = process.exitValue();
        log.log(Level.WARNING, "exit code=" + resultCode);
        if (resultCode < 0 || resultCode > 1) {
            log.log(Level.WARNING, "cmd error.code=" + resultCode);
        }
        commonRead(process.getErrorStream(), runBack, true);
        commonRead(process.getInputStream(), runBack, false);
        runBack.onEnd(cmdStr.toString(), resultCode);
        return resultCode;
    }

    private static void commonRead(InputStream inputStream, final RunBack runBack
            , final boolean isError) {
        try {
            FileUtils.readLine(inputStream, new FileUtils.LineBack() {
                @Override
                public void onStart(String fileName) {
                }

                @Override
                public void onLine(String line) {
                    if (isError) {
                        runBack.onError(line);
                    } else {
                        runBack.onMessage(line);
                    }
                }

                @Override
                public void onEnd(String fileName) {
                }
            });
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
        } finally {
            CloseUtils.closeSilently(inputStream);
        }
    }

    public static interface RunBack {

        public void onStart(String cmd);

        public void onMessage(String message);

        public void onError(String message);

        public void onEnd(String cmd, int exitCode);
    }
}