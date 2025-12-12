package org.firstinspires.ftc.teamcode.utils;
/**
 * FTC adaptation of WPILib's Debouncer class.
 * Requires that a boolean value change from baseline for a specified period of time
 * before the filtered value changes.
 */
public class Debouncer {
    /**
     * Type of debouncing to perform.
     */
    public enum DebounceType {
        /** Rising edge - requires input to be true for debounce time */
        kRising,
        /** Falling edge - requires input to be false for debounce time */
        kFalling,
        /** Both rising and falling edges */
        kBoth
    }

    private double m_debounceTimeSeconds;
    private DebounceType m_debounceType;
    private boolean m_baseline;
    private long m_prevTimeNanos;

    /**
     * Creates a new Debouncer.
     *
     * @param debounceTime The number of seconds the value must change from baseline
     *                     for the filtered value to change.
     * @param type Which type of state change the debouncing will be performed on.
     */
    public Debouncer(double debounceTime, DebounceType type) {
        m_debounceTimeSeconds = debounceTime;
        m_debounceType = type;

        resetTimer();

        // Set initial baseline based on debounce type
        switch (m_debounceType) {
            case kBoth:
            case kRising:
                m_baseline = false;
                break;
            case kFalling:
                m_baseline = true;
                break;
        }
    }

    /**
     * Creates a new Debouncer. Baseline value defaulted to "false."
     *
     * @param debounceTime The number of seconds the value must change from baseline
     *                     for the filtered value to change.
     */
    public Debouncer(double debounceTime) {
        this(debounceTime, DebounceType.kRising);
    }

    /**
     * Creates a new Debouncer with default 0.2 seconds (200ms) debounce time.
     * Useful for sensor debouncing.
     */
    public Debouncer() {
        this(0.2, DebounceType.kRising);
    }

    private void resetTimer() {
        m_prevTimeNanos = System.nanoTime();
    }

    private boolean hasElapsed() {
        double elapsedSeconds = (System.nanoTime() - m_prevTimeNanos) / 1e9;
        return elapsedSeconds >= m_debounceTimeSeconds;
    }

    /**
     * Applies the debouncer to the input stream.
     *
     * @param input The current value of the input stream.
     * @return The debounced value of the input stream.
     */
    public boolean calculate(boolean input) {
        // If input matches baseline, reset the timer
        if (input == m_baseline) {
            resetTimer();
        }

        // If enough time has passed with input different from baseline
        if (hasElapsed()) {
            if (m_debounceType == DebounceType.kBoth) {
                m_baseline = input;  // Update baseline for next transition
                resetTimer();
            }
            return input;  // Return the new stable state
        } else {
            return m_baseline;  // Return the old stable state
        }
    }

    /**
     * Simplified method for FTC use - updates and returns debounced value.
     *
     * @param input The current sensor/input value.
     * @return Debounced value.
     */
    public boolean update(boolean input) {
        return calculate(input);
    }

    /**
     * Gets the current debounced value without updating.
     *
     * @return Current debounced value.
     */
    public boolean getValue() {
        return m_baseline;
    }

    /**
     * Resets the debouncer to its initial state.
     */
    public void reset() {
        resetTimer();
        switch (m_debounceType) {
            case kBoth:
            case kRising:
                m_baseline = false;
                break;
            case kFalling:
                m_baseline = true;
                break;
        }
    }

    /**
     * Sets the time to debounce.
     *
     * @param time The number of seconds the value must change from baseline
     *             for the filtered value to change.
     */
    public void setDebounceTime(double time) {
        m_debounceTimeSeconds = time;
    }

    /**
     * Gets the time to debounce.
     *
     * @return The number of seconds the value must change from baseline
     *         for the filtered value to change.
     */
    public double getDebounceTime() {
        return m_debounceTimeSeconds;
    }

    /**
     * Sets the debounce type.
     *
     * @param type Which type of state change the debouncing will be performed on.
     */
    public void setDebounceType(DebounceType type) {
        m_debounceType = type;
    }

    /**
     * Gets the debounce type.
     *
     * @return Which type of state change the debouncing will be performed on.
     */
    public DebounceType getDebounceType() {
        return m_debounceType;
    }
}
