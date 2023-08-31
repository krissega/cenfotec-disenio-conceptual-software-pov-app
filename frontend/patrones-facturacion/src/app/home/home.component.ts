import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from '../login/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { Document } from '../Models/Document';
import { User } from '../Models/User';
import { Tax } from '../Models/Tax';
import { Discount } from '../Models/Discount';
import { Product } from '../Models/Product';




@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  documents: Document[] = [];
  users: User[] = [];
  usuariosJuridicos: User[] = [];
  usuariosFisicos: User[] = [];
  document: Document = new Document;
  products: Product[] = [];
  product: Product = new Product;
  isLoggedIn = false;
  private currentLoggedUser = new User();
  documentFilterText: string = "Todas";
  selectedUserOption: string = "NA";
  comment: string = "";
  disableUserDropdown: boolean = false;
  selectedUserForTiqueteDeCajas =  new User();
  productsForDocument: Product[] = [];
  productsAddedForDocument: Product[] = [];
  qtyProductAddedForDocument: number = 0;
  selectedProductOption: Product = new Product();
  allProductsDocumentSubTotal: number = 0;
  allProductsDocumentTotal: number = 0;
  taxes: Tax[] = [];
  taxId: string | null = null;
  discounts: Discount[] = [];
  discountId: string | null = null;
  selectedDiscount: Discount | null = null;
  selectedTax: Tax | null = null;
  selectedUserForDocument: User = new User();
  selectedDocumentType: string = "";

  @ViewChild('closeCreateDocumentModal') myButtonRef!: ElementRef;


  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();
    if (this.isLoggedIn) {
      this.currentLoggedUser = JSON.parse(this.authenticationService.getLoggedInUser());
      const authHeader = this.authenticationService.getAuthToken();
      const headers = new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': authHeader,
        'responseType': 'json'
      });
      //Get All taxes
      this.http.get<Tax[]>('http://127.0.0.1:8081/tax/list', {
        headers: headers,
        responseType: 'json'
      }).subscribe(
        (response) => {
          this.taxes = response;
        },
        (error) => {
          console.error('Error fetching Taxes:', error);
        });
      //Get All discounts
      this.http.get<Discount[]>('http://127.0.0.1:8081/discount/list', {
        headers: headers,
        responseType: 'json'
      }).subscribe(
        (response) => {
          this.discounts = response;
        },
        (error) => {
          console.error('Error fetching Taxes:', error);
        });
      //Get all Documents
      this.http.get<Document[]>('http://127.0.0.1:8081/document/all', {
        headers: headers,
        responseType: 'json'
      }).subscribe(
        (response) => {
          this.documents = response;

        },
        (error) => {
          console.error('Error fetching documents:', error);
        }
      );
      //Get all Users
      this.http.get<User[]>('http://127.0.0.1:8081/user/list', {
        headers: headers,
        responseType: 'json'
      }).subscribe(
        (response) => {
          this.users = response;
          this.usuariosJuridicos = this.users.filter(user => user.userType === "User_juridic");
          this.usuariosFisicos = this.users.filter(user => user.userType === "User_human");
        },
        (error) => {
          console.error('Error fetching Users:', error);
        }
      );
    }else{
      this.router.navigate(['/login']);
    }

  }

  loadDocument(idDocument: string){
    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
    this.http.get<Document>('http://127.0.0.1:8081/document/show/'+idDocument, {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        this.document = response;
        console.log(this.document);
        this.document.products.forEach(productData => {
          const qty = productData.qty;
          const product = productData.product;
          if (product !== null) {
            const productId = product.id;
            const productName = product.name;
            const productDescription = product.description;
            const productPrice = product.price;
            product.subtotal = productPrice * qty;
            this.product = product;
          } else {
            console.log('La variable product es nula.');
          }
        });
      },
      (error) => {
        console.error('Error fetching documents:', error);
      }
    );
  }

  onFilteredDocuments(state: string) {
    let url = "";
    console.log("state: "+state);

    if(this.selectedUserOption === "NA"){
      console.log("Selected User:" + this.selectedUserOption);
      url = "http://127.0.0.1:8081/document/filterbystate/"+state;
    }else{
      url = "http://127.0.0.1:8081/document/filterbystate/"+this.selectedUserOption+"/"+state;
    }
    switch(state) {
      case "PENDIENTE_DE_PAGO": {
        this.documentFilterText = "Pendientes de pago";
        break;
      }
      case "PAGADO": {
        this.documentFilterText = "Pagado";
        break;
      }
      case "ANULADO": {
        this.documentFilterText = "Anulados";
        break;
      }
      case "TODAS": {
        this.documentFilterText = "Todas";
        state = "all"
        if(this.selectedUserOption != "NA"){
          url = "http://127.0.0.1:8081/document/list/"+this.selectedUserOption;
        }else{
          url = "http://127.0.0.1:8081/document/all";
        }
        break;
      }
    }

    if (this.isLoggedIn) {
      this.currentLoggedUser = JSON.parse(this.authenticationService.getLoggedInUser());
      const authHeader = this.authenticationService.getAuthToken();
      const headers = new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': authHeader,
        'responseType': 'json'
      });
      this.http.get<Document[]>(url, {
        headers: headers,
        responseType: 'json'
      }).subscribe(
        (response) => {
          this.documents = response;

        },
        (error) => {
          console.error('Error fetching documents:', error);
        }
      );
    }else{
      this.router.navigate(['/login']);
    }
  }

  onChangeUser(event: any) {
    const selectedOptionValue = event.target.value;


    console.log('ID seleccionado:', selectedOptionValue);
    console.log('Value seleccionado:', this.selectedUserOption);
    if(this.selectedUserOption ==="NA"){
      return;
    }
    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
    //Get User documents
    this.http.get<Document[]>('http://127.0.0.1:8081/document/list/'+selectedOptionValue, {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        this.documents = response;
      },
      (error) => {
        console.error('Error fetching Users:', error);
      });

  }

  createDocumentonChangeUser(event: any) {
    const selectedOptionValue = event.target.value;
    this.selectedDocumentType = event.target.value;
    if(selectedOptionValue === "TIQUETE_DE_CAJA"){
      console.log("Tiquete de cajas seleccionado");
      this.disableUserDropdown = true;
      this.selectedUserForTiqueteDeCajas.id ="64f05894949ec7773168c685";
      this.selectedUserForTiqueteDeCajas.userType="User_human";
      this.selectedUserForTiqueteDeCajas.username="tiqueteCajas";
      this.selectedUserForTiqueteDeCajas.password="1234";
      this.selectedUserForTiqueteDeCajas.lastname="system";
      this.selectedUserForTiqueteDeCajas.address="Escazu";
      this.selectedUserForTiqueteDeCajas.email= "facturacion3@email.com";
      this.selectedUserForTiqueteDeCajas.cedula= "801169540";
      this.selectedUserForTiqueteDeCajas.name="system";

    }else {
      this.disableUserDropdown = false;
    }

    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
    //Get All products
    this.http.get<Product[]>('http://127.0.0.1:8081/product/list', {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        this.productsForDocument = response;
      },
      (error) => {
        console.error('Error fetching Users:', error);
      }
    );

  }

  onSelectUser(event: any){

  }

  addProductToDocument(){
    let currentProduct = new  Product();
    //this.productsAddedForDocument.push(this.selectedProductOption);
    console.log(this.selectedProductOption, this.qtyProductAddedForDocument);
    if(this.qtyProductAddedForDocument == 0){
      alert("Please change the QTY for selected product, must be higher than 0");
      return;
    }
    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
    //Get All products
    this.http.get<Product>('http://127.0.0.1:8081/product/show/'+this.selectedProductOption, {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        currentProduct = response;
        currentProduct.qty = this.qtyProductAddedForDocument;
        currentProduct.subtotal = this.qtyProductAddedForDocument * currentProduct.price;
        this.productsAddedForDocument.push(response);
        this.allProductsDocumentSubTotal = 0;
        for (const element of this.productsAddedForDocument) {
          console.log("subtotal de cada elemento: "+element.subtotal);
          this.allProductsDocumentSubTotal += element.subtotal;
        }
      },
      (error) => {
        console.error('Error fetching Product:', error);
      });
  }

  addDiscount(discountId: string | null) {
    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
    //Get User documents
    this.http.get<Discount>('http://127.0.0.1:8081/discount/show/'+discountId, {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        this.selectedDiscount = response;
        console.log("Received discount: "+this.selectedDiscount.id, this.selectedDiscount.description, this.selectedDiscount.percentage);
        if (this.selectedDiscount) {
          let subtotal = this.allProductsDocumentSubTotal;
          let total = subtotal - (subtotal * (this.selectedDiscount.percentage / 100));
          this.allProductsDocumentTotal = total;
        }else{
          alert("Descuento no disponible");
        }
      },
      (error) => {
        console.error('Error fetching Users:', error);
      });


  }


  addTax(idTax: string | null){
    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
    //Get User documents
    this.http.get<Tax>('http://127.0.0.1:8081/tax/show/'+idTax, {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        this.selectedTax = response;
        console.log("Received discount: "+this.selectedTax.id, this.selectedTax.description, this.selectedTax.percentage);
        if (this.selectedTax) {
          let subtotal = this.allProductsDocumentSubTotal;
          let total    = subtotal + (subtotal * (this.selectedTax.percentage/100));
          this.allProductsDocumentTotal = total;

        }else{
          alert("Descuento no disponible");
        }
      },
      (error) => {
        console.error('Error fetching Users:', error);
      });


  }

  obtenerFechas() {
    const fechaActual = new Date();
    const fechaCon7DiasMas = new Date(fechaActual);
    fechaCon7DiasMas.setDate(fechaCon7DiasMas.getDate() + 7);

    const obtenerComponentesFecha = (fecha: Date) => {
      const anio = fecha.getFullYear();
      const mes = String(fecha.getMonth() + 1).padStart(2, '0');
      const dia = String(fecha.getDate()).padStart(2, '0');
      const horas = String(fecha.getHours()).padStart(2, '0');
      const minutos = String(fecha.getMinutes()).padStart(2, '0');
      const segundos = String(fecha.getSeconds()).padStart(2, '0');
      return `${anio}-${mes}-${dia}T${horas}:${minutos}:${segundos}`;
    };

    const fechaActualFormateada = obtenerComponentesFecha(fechaActual);
    const fechaCon7DiasMasFormateada = obtenerComponentesFecha(fechaCon7DiasMas);

    return {
      fechaActual: fechaActualFormateada,
      fechaCon7DiasMas: fechaCon7DiasMasFormateada,
    };
  }

  sendDocument(){
    const fechas = this.obtenerFechas();
    let productsString = "";//JSON.stringify(this.productsAddedForDocument);
    const taxString =  JSON.stringify(this.selectedTax);
    const discountString  =  JSON.stringify(this.selectedDiscount);
    this.productsAddedForDocument.forEach(productData => {
      productsString += `{
          "product":{
              "id": "${productData.id}",
              "name": "${productData.name}",
              "description": "${productData.description}",
              "qtyStock": "${productData.qtyStock}",
              "price": "${productData.price}"
          },
          "qty": "${productData.qty}"
      },`;});
    productsString = productsString.substring(0, productsString.length - 1);
    console.log("Product String: "+productsString);
    let userString="";
    userString+=`{
     "id":"64f05aa279c5640ab2742dea",
      "userType": "User_human",
      "username": "tiqueteCajas",
      "password": "1234",
      "cedula": "801169540",
     "name": "system",
     "lastname": "system",
     "address": "Escazu",
     "email": "facturacion3@email.com"
    }`;

    const documentJson = `{
        "state": "PENDIENTE_DE_PAGO",
        "approved": "false",
        "documentType": "${this.selectedDocumentType}",
        "comment": "${this.comment}",
        "createdAt": "${fechas.fechaActual}",
        "valid": "${fechas.fechaCon7DiasMas}",
        "idDiscount": "${this.selectedDiscount?.id}",
        "idTax": "${this.selectedTax?.id}",
        "idUser": ${userString},
        "products": [${productsString}],
        "tax": ${taxString},
        "discount": ${discountString},
        "total": ${ this.allProductsDocumentTotal}
    }`;
    console.log(documentJson);


    const authHeader = this.authenticationService.getAuthToken();
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': authHeader,
      'responseType': 'json'
    });
// Realizar la petición POST
    this.http.post<Document>('http://127.0.0.1:8081/document/create', documentJson, {
      headers: headers,
      responseType: 'json'
    }).subscribe(
      (response) => {
        const createdDocument = response;
        console.log(createdDocument);
        if(createdDocument){
          this.myButtonRef.nativeElement.click();
          this.onFilteredDocuments('TODAS');
          alert("Document created correctly");
          location.reload();
        }
      },
      (error) => {
        console.error('Error fetching Users:', error);
      });
  }

  approveDocument(documentId: string) {
    console.log(documentId);

    const url = `http://127.0.0.1:8081/document/${documentId}/adminapprove`;

    this.http.put(url, {}).subscribe(
      () => {
        console.log('Documento aprobado exitosamente.');
        location.reload();

      },
      (error) => {
        console.error('Error al aprobar el documento:', error);

      }
    );
  }

  rejectDocument(documentId: string) {
    const message = prompt('Ingrese el motivo del rechazo:');
    if (message === null || message.trim() === '') {
      return;
    }

    const url = `http://127.0.0.1:8081/document/${documentId}/cancel`;
    const requestBody = { comment: message };

    this.http.put(url, requestBody).subscribe(
      () => {
        console.log('Documento rechazado exitosamente.');
        alert('Documento anulado exitosamente.')
        location.reload();
      },
      (error) => {
        console.error('Error al rechazar el documento:', error);

      }
    );
  }


  handleLogout() {
    this.authenticationService.logout();
  }

}
