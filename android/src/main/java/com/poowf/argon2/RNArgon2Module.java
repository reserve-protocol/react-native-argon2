package com.poowf.argon2;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;

import org.signal.argon2.Argon2;

public class RNArgon2Module extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;

    public RNArgon2Module(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNArgon2Module";
    }

    @ReactMethod
    public void argon2(String password, String salt) {
        try {
            Argon2 argon2 = new Argon2.Builder(Version.V13)
                    .type(Type.Argon2id)
                    .memoryCost(MemoryCost.MiB(32))
                    .parallelism(1)
                    .iterations(3)
                    .build();

            final byte[] passwordBytes = password.getBytes("UTF-8");
            final byte[] saltBytes = salt.getBytes("UTF-8");

            Argon2.Result result = argon2.hash(passwordBytes, saltBytes);
            promise.resolve(result);
        } catch (Exception exception) {
            promise.reject("Failed to generate argon2 hash", exception);
        }

    }
}
