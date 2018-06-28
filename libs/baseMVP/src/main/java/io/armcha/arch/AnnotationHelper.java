package io.armcha.arch;

/**
 */

class AnnotationHelper {

    static BaseMVPContract.Presenter createPresenter(Class<?> annotatedClass) {
        try {
            return annotatedClass.getAnnotation(Viewable.class).presenter().newInstance();
        } catch (InstantiationException e) {
            throw new MvpException("Cannot create an instance of " + annotatedClass, e);
        } catch (IllegalAccessException e) {
            throw new MvpException("Cannot create an instance of " + annotatedClass, e);
        }
    }
}
