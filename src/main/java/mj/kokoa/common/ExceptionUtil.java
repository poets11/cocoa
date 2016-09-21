package mj.kokoa.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by poets11 on 2016. 9. 20..
 */
public class ExceptionUtil {
    public static String getStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);

        writer.flush();
        writer.close();

        return stringWriter.getBuffer().toString();
    }
}
