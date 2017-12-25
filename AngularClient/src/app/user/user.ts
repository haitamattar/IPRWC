export class User {
  constructor(
    public id?: number,
    public email?: string,
    public password?: string,
    public fullname?: string,
    public postalcode?: string,
    public streetnumber?: number,
    public role?: string
    ) {
  }
}
