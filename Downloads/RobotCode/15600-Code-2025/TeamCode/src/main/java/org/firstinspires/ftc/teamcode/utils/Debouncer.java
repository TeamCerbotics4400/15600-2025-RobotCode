package org.firstinspires.ftc.teamcode.utils;

public class Debouncer {

    private final double debounceTime;
    private double lastChangeTime = 0;
    private boolean debouncedState = false;

    // only debounce FALSE→TRUE transitions
    private final boolean debounceFalseOnly;

    public Debouncer(double debounceTime) {
        this(debounceTime, false);
    }

    public Debouncer(double debounceTime, boolean debounceFalseOnly) {
        this.debounceTime = debounceTime;
        this.debounceFalseOnly = debounceFalseOnly;
    }

    /**
     * Calculates the debounced state.
     * The debounced state stays TRUE when the raw state is TRUE.
     * When the raw state changes from TRUE to FALSE, the debounced state
     * will only change after debounceTime has elapsed.
     * @param raw The current raw input state.
     * @return The debounced state.
     */
    public boolean calculate(boolean raw) {
        double now = System.nanoTime() / 1e9;

        // --- Scenario 1: Raw input is TRUE ---
        // If the raw input is TRUE, the debounced state should follow immediately
        // (i.e., FALSE -> TRUE transition is IMMEDIATE)
        if (raw) {
            debouncedState = true;
            lastChangeTime = now; // Reset time so we're ready for the TRUE -> FALSE delay
            return debouncedState;
        }

        // --- Scenario 2: Raw input is FALSE (TRUE -> FALSE transition) ---
        // Debounced state can only change to FALSE after debounceTime has elapsed
        // from the moment the raw input went FALSE (which is when lastChangeTime was set to now)

        // Check if the current debounced state is TRUE AND the time has elapsed
        if (debouncedState) {
            // Raw state is FALSE, so we are now waiting for the debounce time
            if ((now - lastChangeTime) >= debounceTime) {
                // Time has passed, transition to FALSE
                debouncedState = false;
            }
            // else: Time hasn't passed, stay TRUE for now
        }

        // Note: If debouncedState is already FALSE, it stays FALSE.
        // The lastChangeTime isn't updated here because the raw input is still FALSE.

        return debouncedState;
    }
}
