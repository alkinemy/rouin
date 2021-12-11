export class Exception {
    private message: string;
    private name: string;

    constructor(message, name) {
        this.message = message;
        this.name = name;
    }
}

export class IllegalArgumentException extends Exception {
    constructor(message) {
        super(message, "IllegalArgumentException");
    }
}