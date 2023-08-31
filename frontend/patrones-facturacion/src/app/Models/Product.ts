import {ProductByDocument} from "./ProductByDocument"

export class Product {
    id: string  = "0";
    name: string = "";
    description: string = "";
    qtyStock: number = 0;
    price: number;
    qty: number;
    documents: ProductByDocument = [];
    product: Product | null = null;
    subtotal: number;

    constructor(
        id?: string,
        name?: string,
        description?: string,
        qtyStock?: number,
        price: number = 0,
        documents?: ProductByDocument,
        qty?: number,
        product?: Product,
        subtotal: number = 0
    ){
        this.id=id || "0";
        this.name=name || "";
        this.description=description || "";
        this.qtyStock=qtyStock || 0;
        this.price=price;
        this.documents=documents || {};
        this.qty=qty || 0;
        this.product || {};
        this.subtotal=subtotal;
    }
}
