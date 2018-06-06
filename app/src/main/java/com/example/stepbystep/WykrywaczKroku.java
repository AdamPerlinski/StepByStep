/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.stepbystep;


public class WykrywaczKroku {
    private float[] AccelX = new float[ROZMIAR_ACCEL];
    private float[] AccelY = new float[ROZMIAR_ACCEL];
    private float[] AccelZ = new float[ROZMIAR_ACCEL];
    private static final int ROZMIAR_ACCEL = 50;
    private static final int ROZMIAR_VELOCITY = 11;
    private static final float THRESHOLDKROKU = 10f;
    private static final int OPOZNIENIEKROKU = 250000000;

    private int LicznikAccel = 0;

    private int LicznikPrzyspieszenie = 0;
    private float[] PrzyspieszenieR = new float[ROZMIAR_VELOCITY];
    private long CzasOstatniegoKroku = 0;
    private float OstatniSzacunek = 0;

    private StepListener listener;

    public void registerListener(StepListener listener) {
        this.listener = listener;
    }


    public void updateAccel(long timeNs, float x, float y, float z) {
        float[] obecnePrzyspieszenie = new float[3];
        obecnePrzyspieszenie[0] = x;
        obecnePrzyspieszenie[1] = y;
        obecnePrzyspieszenie[2] = z;

        // Pierwszy krok służy, do wskazania globalnego wektora Z.
        LicznikAccel++;
        AccelX[LicznikAccel % ROZMIAR_ACCEL] = obecnePrzyspieszenie[0];
        AccelY[LicznikAccel % ROZMIAR_ACCEL] = obecnePrzyspieszenie[1];
        AccelZ[LicznikAccel % ROZMIAR_ACCEL] = obecnePrzyspieszenie[2];

        float[] realZ = new float[3];
        realZ[0] = MetodyNumeryczne.sum(AccelX) / Math.min(LicznikAccel, ROZMIAR_ACCEL);
        realZ[1] = MetodyNumeryczne.sum(AccelY) / Math.min(LicznikAccel, ROZMIAR_ACCEL);
        realZ[2] = MetodyNumeryczne.sum(AccelZ) / Math.min(LicznikAccel, ROZMIAR_ACCEL);

        float wskaznikNormalizacji = MetodyNumeryczne.normalize(realZ);

        realZ[0] = realZ[0] / wskaznikNormalizacji;
        realZ[1] = realZ[1] / wskaznikNormalizacji;
        realZ[2] = realZ[2] / wskaznikNormalizacji;


        // Tutaj liczymy component akceleracji
        // w kierunku zmiennej realZ i odejmujemy akceleracje
        float ObecneZ = MetodyNumeryczne.punkt(realZ, obecnePrzyspieszenie) - wskaznikNormalizacji;
        LicznikPrzyspieszenie++;
        PrzyspieszenieR[LicznikPrzyspieszenie % ROZMIAR_VELOCITY] = ObecneZ;

        float ObecnySzacunek = MetodyNumeryczne.sum(PrzyspieszenieR);

        if (ObecnySzacunek > THRESHOLDKROKU && OstatniSzacunek <= THRESHOLDKROKU
                && (timeNs - CzasOstatniegoKroku > OPOZNIENIEKROKU)) {
            listener.step(timeNs);
            CzasOstatniegoKroku = timeNs;
        }
        OstatniSzacunek = ObecnySzacunek;
    }
}