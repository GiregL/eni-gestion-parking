package fr.eni.gestionParking.bo;

import java.util.function.Consumer;

public class Pair <T, S>{
    private T left;
    private S right;

    public Pair(T left, S right) {
        this.left = left;
        this.right = right;
    }

    private static <U> void ifPresentOrElse(U predicate, Consumer<U> consumer, EmptyConsumer els) {
        if (predicate != null) {
            consumer.accept(predicate);
        } else {
            els.apply();
        }
    }

    private static <U> void ifPresent(U predicate, Consumer<U> consumer) {
        ifPresentOrElse(predicate, consumer, () -> {});
    }

    public void ifLeftPresentOrElse(Consumer<T> consumer, EmptyConsumer els) {
        ifPresentOrElse(this.left, consumer, els);
    }

    public void ifRightPresentOrElse(Consumer<S> consumer, EmptyConsumer els) {
        ifPresentOrElse(this.right, consumer, els);
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public S getRight() {
        return right;
    }

    public void setRight(S right) {
        this.right = right;
    }
}
