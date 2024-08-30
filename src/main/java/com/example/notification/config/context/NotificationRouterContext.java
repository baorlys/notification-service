package com.example.notification.config.context;

import com.example.notification.enums.TargetOutput;
import com.example.notification.input.SenderInfo;
import com.example.notification.model.TemplateMessage;

public class NotificationRouterContext {
    private static final ThreadLocal<TemplateMessage> templateHolder = new ThreadLocal<>();
    private static final ThreadLocal<SenderInfo> fromHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> subjectHolder = new ThreadLocal<>();

    private static final ThreadLocal<TargetOutput> targetHolder = new ThreadLocal<>();

    private NotificationRouterContext() {
        // static class
    }

    public static TemplateMessage getTemplate() {
        return templateHolder.get();
    }

    public static void setTemplate(TemplateMessage templateMessage) {
        templateHolder.set(templateMessage);
    }

    public static SenderInfo getFrom() {
        return fromHolder.get();
    }

    public static void setFrom(SenderInfo from) {
        fromHolder.set(from);
    }

    public static String getSubject() {
        return subjectHolder.get();
    }

    public static void setSubject(String subject) {
        subjectHolder.set(subject);
    }

    public static TargetOutput getTargetOutput() {
        return targetHolder.get();
    }

    public static void setTargetOutput(TargetOutput targetOutput) {
        targetHolder.set(targetOutput);
    }

    public static void set(TargetOutput target, SenderInfo from, String subject, TemplateMessage templateMessage) {
        setTargetOutput(target);
        setFrom(from);
        setSubject(subject);
        setTemplate(templateMessage);
    }

    public static void clear() {
        targetHolder.remove();
        templateHolder.remove();
        fromHolder.remove();
        subjectHolder.remove();
    }
}
