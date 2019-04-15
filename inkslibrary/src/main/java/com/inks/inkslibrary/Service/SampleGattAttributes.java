/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inks.inkslibrary.Service;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00001003-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

//
    public static UUID READ2 = UUID.fromString("00001002-0000-1000-8000-00805f9b34fb");
    public static UUID WRITE2 = UUID.fromString("00001001-0000-1000-8000-00805f9b34fb");

    public static UUID READ = UUID.fromString("00004a5b-0000-1000-8000-00805f9b34fb");
    public static UUID WRITE = UUID.fromString("00004a5b-0000-1000-8000-00805f9b34fb");

    public static UUID WRITE_SERVICE = UUID.fromString("00001001-0000-1000-8000-00805f9b34fb");
    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }

    public static UUID getREAD2() {
        return READ2;
    }

    public static UUID getWRITE2() {
        return WRITE2;
    }

    public static UUID getREAD() {
        return READ;
    }

    public static UUID getWRITE() {
        return WRITE;
    }

    public static void setREAD2(UUID READ2) {
        SampleGattAttributes.READ2 = READ2;
    }
    public static void setWRITE2(UUID WRITE2) {
        SampleGattAttributes.WRITE2 = WRITE2;
    }
    public static void setREAD(UUID READ) {
        SampleGattAttributes.READ = READ;
    }
    public static void setWRITE(UUID WRITE) {
        SampleGattAttributes.WRITE = WRITE;
    }
}
