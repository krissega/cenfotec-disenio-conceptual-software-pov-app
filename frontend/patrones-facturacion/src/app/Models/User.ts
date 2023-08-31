export class User {
    id: string  = "0";
    userType: string = "";
    username: string = "";
    password: string = "";
    cedula: string = "";
    name: string = "";
    lastname: string = "";
    address: string = "";
    cedulaJuridica: string = "";
    razonSocial: string = "";
    email: string = "";
    authenticated:Number = 0;

    constructor(
        id?: string,
        userType?: string,
        username?: string,
        password?: string,
        cedula?: string,
        name?: string,
        lastname?: string,
        address?: string,
        cedulaJuridica?: string,
        razonSocial?: string,
        email?: string,
        authenticated?: number
      ) {
        this.id = id || "0s";
        this.userType = userType || "";
        this.username = username || "";
        this.password = password || "";
        this.cedula = cedula || "";
        this.name = name || "";
        this.lastname = lastname || "";
        this.address = address || "";
        this.cedulaJuridica = cedulaJuridica || "";
        this.razonSocial = razonSocial || "";
        this.email = email || "";
        this.authenticated = authenticated || 0;
      }


}
