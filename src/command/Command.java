package command;

import parser.ParsedCommand;

public interface Command {
    // Η εντολή δέχεται πλέον όλη τη δομημένη πληροφορία από τον Parser
    String execute(ParsedCommand command);
}