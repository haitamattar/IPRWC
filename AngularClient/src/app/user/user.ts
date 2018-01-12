export class User {
  constructor(
    public id?: number,
    public email?: string,
    public password?: string,
    public fullName?: string,
    public postalcode?: string,
    public city?: string,
    public street?: string,
    public streetnumber?: number,
    public role: string = 'CUSTOMER'
    ) {
  }
}
