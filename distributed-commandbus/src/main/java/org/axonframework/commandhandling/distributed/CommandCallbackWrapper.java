package org.axonframework.commandhandling.distributed;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;

/**
 * Wrapper for a Command callback. This is used in a CommandCallbackRepository
 *
 * @author Koen Lavooij
 *
 * @param <A> The type of the session identifier
 * @param <C> The type of the command
 * @param <R> The type of the expected result
 */
public class CommandCallbackWrapper<A, C, R> implements CommandCallback<C, R> {
    private final CommandCallback<? super C, R> wrapped;
    private final A sessionId;
    private final CommandMessage<C> message;

    public CommandCallbackWrapper(A sessionId, CommandMessage<C> message, CommandCallback<? super C, R> callback) {
        this.wrapped = callback;
        this.sessionId = sessionId;
        this.message = message;
    }

    public CommandMessage<C> getMessage() {
        return message;
    }

    public A getChannelIdentifier() {
        return sessionId;
    }

    public void fail(Throwable e) {
        onFailure(getMessage(), e);
    }

    public void success(R returnValue) {
        onSuccess(getMessage(), returnValue);
    }

    @Override
    public void onSuccess(CommandMessage<? extends C> message, R result) {
        wrapped.onSuccess(message, result);
    }

    @Override
    public void onFailure(CommandMessage<? extends C> message, Throwable cause) {
        wrapped.onFailure(message, cause);
    }
}
