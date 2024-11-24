#!/bin/bash

# Define las rutas de origen y destino para la copia de seguridad
origen="src/main/resources/co/edu/uniquindio/icaja"
backup_dir="src/main/resources/co/edu/uniquindio/icaja/backup"

# Crea el directorio de respaldo si no existe
mkdir -p "$backup_dir"

# Realiza una copia de seguridad de todos los archivos .fxml antes de realizar cambios
echo "Realizando copia de seguridad de los archivos .fxml..."
find "$origen" -name "*.fxml" -exec cp {} "$backup_dir" \;

# Verifica que la copia de seguridad se haya realizado correctamente
if [ $? -eq 0 ]; then
    echo "Copia de seguridad realizada con éxito."
else
    echo "Error al realizar la copia de seguridad. Abortando..."
    exit 1
fi

# Encuentra y reemplaza las versiones 22 por 21 en todos los archivos .fxml
echo "Actualizando archivos .fxml de la versión 22 a 21..."
find "$origen" -name "*.fxml" -exec sed -i 's/22/21/g' {} \;

# Verifica si el reemplazo se realizó correctamente
if [ $? -eq 0 ]; then
    echo "Archivos .fxml actualizados correctamente."
else
    echo "Error al actualizar los archivos .fxml."
    exit 1
fi

echo "Proceso completado con éxito."
