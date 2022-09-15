# Meli-Coupon
Software para la gestión de la optimización de la sumatoria de un listado de productos dentro de un tope dado

1.0 PRESENTADO POR: Andrés Fernando Hincapié Sánchez

2.0 DESCRIPCIÓN DE LA SOLUCIÓN: 
El algoritmo elegido para la solución del problema consiste en:
a) Obtener la diferencia entre el precio de los ítems solicitados por el usuario y el monto del cupón
b) Ordenar los precios de menor a mayor
c) Recorrer los precios ordenados y eliminar de la lista la menor diferencia encontrada

3.0 PRERREQUISITOS:
Creación de los datos en la base de datos H2 incorporada al desarrollo

4.0 ENTRADA:
{
"item_ids": ["MLA1", "MLA2", "MLA3", "MLA4", "MLA5"],
"amount": 500
}

5.0 SALIDA:
{
    "item_ids": [
        "MLA2",
        "MLA4",
        "MLA5",
        "MLA1"
    ],
    "total": 480.0
}
