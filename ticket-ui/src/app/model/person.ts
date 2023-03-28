export interface Person {
    id ?: number;
    name ?: string;
    type ?: typePerson;
}

export enum typePerson {
    USER = "User",
    SUPPORTER = "Supporter"
}