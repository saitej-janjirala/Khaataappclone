package com.saitejajanjirala.digitaludhaarkhata;

import android.content.Context;



import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component
public interface Check {
    void inject(Enterphonenumber enterphonenumber);
    void injection(MainActivity mainActivity);
    void injection1(Otp otp);
    void injection2(Newkhaataentry newkhaataentry);
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(@Named("enternumber context") Context context);
        @BindsInstance
        Builder dialogcontext(@Named("dialog context") Context context);
        @BindsInstance
        Builder dialogtitle(  @Named("dialogtitle") String title);
        @BindsInstance
        Builder dialogmessage(@Named("dialogmessage") String message);
        @BindsInstance
        Builder userid(@Named ("muid")String uid);
        Check build();
    }
}
