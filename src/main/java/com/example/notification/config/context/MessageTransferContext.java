package com.example.notification.config.context;

import com.example.notification.enums.TargetOutput;
import com.example.notification.input.NotificationRequest;
import com.example.notification.input.SenderInfo;
import com.example.notification.model.TemplateMessage;

public class MessageTransferContext {
    private static final ThreadLocal<TemplateMessage> templateHolder = new ThreadLocal<>();
    private static final ThreadLocal<SenderInfo> fromHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> subjectHolder = new ThreadLocal<>();

    private static final ThreadLocal<TargetOutput> targetHolder = new ThreadLocal<>();

    private MessageTransferContext() {
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

    public static void set(NotificationRequest req) {
        setTargetOutput(req.getTargetOutput());
        setFrom(req.getFrom());
        setSubject(req.getSubject());
    }

    public static void clear() {
        targetHolder.remove();
        templateHolder.remove();
        fromHolder.remove();
        subjectHolder.remove();
    }
}
