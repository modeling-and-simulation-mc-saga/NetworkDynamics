package sis;

import java.util.List;

/**
 *
 * @author tadaki
 */
public class Individual {

    public static enum State {
        Susceptible, Infected;
    }

    private State state;
    private final double susceptibility;
    private final double recovery;

    public Individual(State state, double susceptibility, double recovery) {
        this.susceptibility = susceptibility;
        this.recovery = recovery;
        this.state = state;
    }

    public Individual(double susceptibility, double recovery) {
        this(State.Susceptible, susceptibility, recovery);
    }

    public State update(List<Individual> neighbours) {
        switch (state) {
            case Susceptible:
                boolean hasInfectedNeibours = false;
                for (Individual ind : neighbours) {
                    if (ind.state == State.Infected) {
                        hasInfectedNeibours = true;
                        break;
                    }
                }
                if (hasInfectedNeibours) {
                    if (Math.random() < susceptibility) {
                        state = State.Infected;
                    }
                }
                break;
            default:
                if (Math.random() < recovery) {
                    state = State.Susceptible;
                }
                break;
        }
        return state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
