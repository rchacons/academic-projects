export class Pokemon {
    constructor(
        public id: number, 
        public name: string,
        public types?: string[],
        public stats? : Stats[],
        public imageUrl? : string
        ){}
}

export interface Stats{
    stat : string
    baseStat : number
}