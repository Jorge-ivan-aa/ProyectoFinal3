# Requisitos Proyecto

## Entidades principales

- [x] **Clase Usuario**: idUsuario, nombre, correo, numero, [!] direccion, saldo total.
- [x] **Clase Cuenta**: idCuenta, entidad, numero cuenta, tipo -> ( Ahorro o corriente).
- [x] **Clase Transaccion**: idTransaccion, fecha, tipo -> (deposito, retiro, transferencia), monto, descripción, cuenta origen, cuenta destino, categoria.
- [x] **Clase Presupuesto**: idPresupuesto, nombre, monto asignado, monto gastado, categoria.
- [x] **Clase Categoria**: idCategoria, nombre, descripción.


## Requerimientos Administrador

- [x] **Gestionar usuarios**: crear, actualizar, eliminar, listar.
- [x] **Gestionar cuentas**: crear, actualizar, eliminar.
- [ ] **Gestionar transacciones**: crear, listar.
- [ ] **Mostrar estadisticas**: gastos comunes, usuarios con más transacciones, saldo promedio de usuarios.
- [ ] **Mostrar graficas**: graficas con JavaFX Charts.



## Requerimientos Usuario

- [x] **Registrarse y loguearse.**
- [ ] **Modificar perfil**: nombre, correo, telefono.
- [ ] **Agregar, retirar y transferir.**
- [ ] **Crear, modificar y eliminar presupuestos.**
- [ ] **Consultar y categorizar transacciones.**
- [ ] **Ver detalle de transacciones**: fecha, monto, descripción.
- [ ] **Recibir notificaciones sobre estado de transacciones.**
- [ ] **Calificar el servicio.**

## Requerimientos adicionales
- [x] **Diagrama de clases**
- [x] **Implementación tecnica**: jdk21, Maven, javaFX, lombok, patron singleton, [!] uso de patrones, excepciones propias (7/10), persistencia, [!] servidor y cliente, [!] hilos, [!] recursividad para consultas y recorrido de listas.
- [ ] **Generador de reportes financieros**: generar reportes detallados sobre ingresos, gastos y saldos en formato PDF o CSV (Apache POI, PDFBox) con opciones para seleccionar tipo de reporte y rango de fechas.
- [ ] **Asistente Financiero virtual**: un chat con asistente financiero que brinde recomendaciones personalizadas.
- [ ] **Implementación patrón productor consumidor**: RabbitMQ u otro.
- [ ] **Sincronización de datos entre instancias.**
- [ ] **Validación de escenarios**: Escenario 1, 2 y 3.
- [ ] **Notificaciones**: El sistema debe notificar al usuario sobre el estado de las transacciones procesadas en cualquier instancia.

### Escenarios:
- **Escenario 1**: Una instancia de la aplicación
*Productor*: El usuario realiza una operación de transferencia en la billetera.
*Consumidor*: La misma instancia procesa la operación y actualiza los datos.
*Sincronización*: No hay problemas de sincronización, ya que todo ocurre en una sola instancia. El enfoque aquí es probar la correcta gestión de la cola y el procesamiento sin pérdida de mensajes.

- **Escenario 2**: Varias instancias de la aplicación
*Productor*: Una instancia genera una operación (por ejemplo, un depósito en una cuenta).
*Consumidor*: Otra instancia recibe el mensaje y procesa la operación.
*Sincronización*: Se verifica que el sistema mantenga la información sincronizada entre las diferentes instancias. Si se realiza un depósito en una instancia, el saldo debe actualizarse en todas las instancias sin duplicación ni pérdida de datos.

- **Escenario 3**: Varias operaciones simultáneas en varias instancias
*Productor*: Varias instancias generan operaciones simultáneamente (transferencias, retiros).
*Consumidor*: Varias instancias consumen los mensajes de las colas y procesan las operaciones.
*Sincronización*: Se deben evaluar casos donde múltiples operaciones se procesan sin que ninguna se pierda o se procese dos veces.
