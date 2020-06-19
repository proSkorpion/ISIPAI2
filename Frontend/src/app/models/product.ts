export class Product {
    carId: number;
    carMake: string;
    model: string;
    price: number;
    imageUrl: string;
    engineCapacity: string;
    type: string;
    fuel: string;
    gearbox: string;
    year: number;
    colour: string;
    emissionsCO2: string;

  convertStringToNumber(price): number{
    return parseFloat(price.replace(/\s/g, ''));
  }
}
