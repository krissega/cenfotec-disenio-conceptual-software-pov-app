export class Discount {
    id: String = "0";
    description: string = "";
    percentage: number = 0;

    constructor(
        id?: string ,
        description?: string,
        percentage?: number
    ){
        this.id = id ||"0";
        this.description = description || "";
        this.percentage = percentage || 0;
    }



}
