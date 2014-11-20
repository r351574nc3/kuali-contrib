#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2014 Leo Przybylski. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Leo Przybylski ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Leo Przybylski.
 */
package ${package}.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.logging.Logger;

import static java.util.logging.Level.*;

/**
 * Class with static methods wrapping {@link Log} methods. Automatically sets up Log for you. It's called the <code>FormattedLogger</code> because
 * it handles everything in ansi-C standard printf format. For example, <code>printf("The epoch time is now %d", new Date().getTime())</code>.<br/>
 * <br/>
 *  
 * To use these just do
 * <code>
 * import ${package}.logging.FormattedLogger.*
 * </code>
 * 
 * @see java.util.logging.Logger
 * @see java.util.logging.Level
 */
public class FormattedLogger {
    
    /**
     * Applies a pattern with parameters to create a {@link String} used as a logging message
     *  
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     * @return Logging Message
     */
    private static final String getMessage(String pattern, Object ... objs) {
        StringWriter retval = new StringWriter();
        PrintWriter writer = new PrintWriter(retval);
        
        writer.printf(pattern, objs);
        
        return retval.toString();
    }
    
    /**
     * Uses {@link StackTraceElement[]} from {@link Throwable} to determine the calling class. Then, the {@link Log} is retrieved for it by
     * convention
     * 
     * 
     * @return Log for the calling class
     */
    private static final Logger getLog() {
        try {
            return Logger.getLogger(new Throwable().getStackTrace()[3].getClassName());
        }
        catch (Exception e) {
            return Logger.getLogger(FormattedLogger.class.getName());
        }
    }

    /**
     * Wraps {@link Log#trace(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void trace(String pattern, Object ... objs) {
        final StackTraceElement element = new Throwable().getStackTrace()[3];
        final Logger log = Logger.getLogger(element.getClassName());
        if (log.isLoggable(FINER)) {
            log.logp(FINER, element.getClassName(), element.getMethodName(), getMessage(pattern, objs));
        }
    }
    
    /**
     * Wraps {@link Logger#entering(String, String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void entering() {
        final StackTraceElement element = new Throwable().getStackTrace()[3];
        final Logger log = Logger.getLogger(element.getClassName());
        log.entering(element.getClassName(), element.getMethodName());
    }

    /**
     * Wraps {@link Logger#exiting(String, String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void exiting() {
        final StackTraceElement element = new Throwable().getStackTrace()[3];
        final Logger log = Logger.getLogger(element.getClassName());
        log.exiting(element.getClassName(), element.getMethodName());
    }

    /**
     * Wraps {@link Log#debug(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void debug(String pattern, Object ... objs) {
        Logger log = getLog();
        if (log.isLoggable(FINE)) {
            log.fine(getMessage(pattern, objs));
        }
    }
    
    public static final boolean isDebuggingEnabled() {
        return getLog().isLoggable(FINE);
    }
    
    /**
     * Wraps {@link Logger#throwing(String)}
     * 
     * @param the thing that was thrown
     */
    public static final void throwing(final Throwable thrown) {
        final StackTraceElement element = new Throwable().getStackTrace()[3];
        final Logger log = Logger.getLogger(element.getClassName());
        log.throwing(element.getClassName(), element.getMethodName(), thrown);
    }

    
    /**
     * Wraps {@link Log#info(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void info(String pattern, Object ... objs) {
        Logger log = getLog();
        if (log.isLoggable(INFO)) {
            log.info(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Logger#config(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void config(String pattern, Object ... objs) {
        Logger log = getLog();
        if (log.isLoggable(CONFIG)) {
            log.config(getMessage(pattern, objs));
        }
    }


    /**
     * Wraps {@link Logger#warning(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void warn(String pattern, Object ... objs) {
        Logger log = getLog();
        if (log.isLoggable(WARNING)) {
            log.warning(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Logger#severe(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void error(String pattern, Object ... objs) {
        Logger log = getLog();
        if (log.isLoggable(SEVERE)) {
            log.severe(getMessage(pattern, objs));
        }
    }
}
