import { User } from "./User";
import { Tax } from "./Tax";
import { Discount } from "./Discount";
import { Product } from "./Product";

export class Document {
    id:string  = "0";
    state: string = "";
    approved: boolean = false;
    documentType: string = "";
    comment: string = "";
    createdAt: Date | null = null;
    valid: Date | null = null;
    idDiscount: string  = "0";
    idTax: string  = "0";
    idUser: string  = "0";
    user: User | null = null;
    tax: Tax | null = null;
    discount: Discount | null = null;
    products: Product[] = [];
    total: number = 0;

    constructor(
        id?: string ,
        state?: string,
        approved?: boolean,
        documentType?: string,
        comment?: string,
        createdAt?: Date,
        valid?: Date,
        idDiscount?: string,
        idTax?: string ,
        idUser?: string ,
        user?: User,
        tax?: Tax,
        discount?: Discount,
        products?: Product[],
        total?: number
      ) {
        this.id = id || "0";
        this.state = state || "";
        this.approved= approved || false;
        this.documentType = documentType || "";
        this.comment= comment || "";
        this.createdAt= createdAt || null
        this.valid= valid || null;
        this.idDiscount= idDiscount ||"0";
        this.idTax= idTax || "0";
        this.idUser= idUser ||"0";
        this.user= user || null;
        this.tax= tax || null;
        this.discount= discount || null;
        this.products= products || [];
        this.total= total || 0;
      }


}
