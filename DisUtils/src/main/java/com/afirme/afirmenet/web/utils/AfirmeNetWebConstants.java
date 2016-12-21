package com.afirme.afirmenet.web.utils;

import java.util.Arrays;
import java.util.List;

public class AfirmeNetWebConstants {
	// ### Definicion de JavaPath para Beans ###//
	public static final String JAVA_PATH = "com.afirme.afirmenet.ibs";
	// ### Administrador de usuarios
	public static final String LISTAR_USUARIOS = "/site/administrador/admin_home-usuarios";
	public static final String TOKENS_DISPONIBLES = "/site/administrador/admin_home-usuarios-tokens";
	// ### Administrador de home
    public static final String LISTAR_NOTIFICACIONES = "/site/administrador/";
    public static final String LISTAR_AUTORIZACIONES = "/site/administrador/";
	// ### Inicia definicion de Paths ###//
	public static final String MODAL = "/base/include/modal";
	public static final String MODAL_EASY_SOLUTIONS = "/site/acceso/modalEasySolutions";
	public static final String MV_LOGIN_AVISO_SEGURIDAD = "/site/acceso/avisoSeguridad";
	public static final String MV_LOGIN_USERINACTIVO = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_USERSUSPENDIDO = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_USERNUEVO = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_USERINACTIVIDAD = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_USERIVALIDO = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_ACCCANCELADA = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_AVISO_ACCCANCELADA = "/site/acceso/avisosLogin";
	public static final String MV_LOGIN_ACCPAGOPYME = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_AVISO_ACCPAGOPYME = "/site/acceso/avisosLogin";
	public static final String MV_LOGIN_DATOS_ACCESO = "/site/acceso/datosAcceso";
	public static final String MV_LOGIN_ACTIVA_CONTRATO_CONFIRMA = "/site/acceso/activaContratoConfirma";
	public static final String MV_LOGIN_ACTIVA_CONTRATO_CONFIRMA_BASICO = "/site/acceso/activaContratoConfirmaBasico";
	public static final String MV_LOGIN = "site/acceso/login";
	public static final String MV_LOGIN_ALIAS = "site/acceso/alias";
	public static final String MV_LOGIN_ALIAS_BASICO = "site/acceso/aliasBasico";
	public static final String MV_LOGIN_AVATAR = "site/acceso/avatar";
	public static final String MV_LOGIN_AVATAR_BASICO = "site/acceso/avatarBasico";
	public static final String MV_LOGIN_ALIAS_CONFIRMA = "site/acceso/aliasConfirma";
	public static final String MV_LOGIN_ALIAS_COMPROBANTE = "site/acceso/aliasComprobante";
	public static final String MV_LOGIN_INVALIDPASS = "site/acceso/mensajesLogin";
	public static final String MV_LOGIN_FINDIA = "site/acceso/login";
	public static final String MV_LOGIN_INOUT = "/site/acceso/mensajesLogin";
	public static final String MV_LOGIN_BANCAMOVIL = "/site/acceso/mensajesLogin";
	public static final String MV_HOME = "site/comun/home";
	public static final String MV_HOME_TERMINOS = "site/comun/terminos";
	public static final String MV_PREG_SEGURIDAD = "site/acceso/preguntaSeguridad";
	public static final String MV_PREG_SEGURIDAD_BASICO = "site/acceso/preguntaSeguridadBasico";
	public static final String MV_RECUPERA_PWD = "site/acceso/recuperaPwd";
	public static final String MV_RECUPERA_PWD_BASICO = "site/acceso/recuperaPwdBasico";
	public static final String MV_RECUPERA_PREG_SEGU = "site/acceso/recuperaPregSeg";
	public static final String MV_SYNC_TOKEN = "site/acceso/sincronizarToken";
	public static final String MV_CONTROL_ACCESO_VALIDA_PASS = "site/comun/home";
	public static final String MV_ACTIVA_CONTRATO = "site/acceso/activaContrato";
	public static final String MV_ACTIVA_CONTRATO_BASICO = "site/acceso/activaContratoBasico";
	public static final String MV_CONTRATO = "site/acceso/contratoBanca";
	public static final String MV_CONTRATO_BASICO = "site/acceso/contratoBancaBasico";
	public static final String MV_ASIGNA_PWD = "site/acceso/asignaPwd";
	public static final String MV_ASIGNA_PWD_BASICO = "site/acceso/asignaPwdbasico";
	public static final String MV_ACTIVA_TOKEN = "site/acceso/activaToken";
	public static final String MV_ACTIVA_CONTRATOSINTOKEN = "site/acceso/activacion";
	public static final String MV_HORARIO_ERROR = "site/comun/horario";
	// #CONSULTAS----Saldos y movimientos#//
	public static final String MV_CONSULTAS_SM_RESUMEN = "site/consultas/sm/resumen";
	public static final String MV_CONSULTAS_SM_DETALLE_ULTIMOS = "site/consultas/sm/detalle_ultimos";
	public static final String MV_CONSULTAS_SM_FONDO_INVERSION= "site/consultas/sm/detalle_fondo_inversion";
	public static final String MV_CONSULTAS_SM_FONDO_INVERSION_MOVIMIENTOS= "site/consultas/sm/detalle_fondo_inversion_movimientos";
	public static final String MV_CONSULTAS_SM_SALDOS_BONUS = "site/consultas/sm/cuentas/bonus/detalle_saldos_bonus";
	public static final String MV_CONSULTAS_SM_DETALLE_TODOS = "site/consultas/sm/detalle_todos";
	public static final String MV_CONSULTAS_SM_DETALLE_RETENIDOS = "site/consultas/sm/detalle_retenidos";
	public static final String MV_CONSULTAS_SM_DETALLE_RETENIDOS_BONUS = "site/consultas/sm/cuentas/bonus/detalle_retenidos_bonus";
	public static final String MV_CONSULTAS_SM_BUSQUEDA_FILTROS = "site/consultas/sm/busqueda_filtros";
	public static final String MV_CONSULTAS_SM_BUSQUEDA_FILTROS_BONUS = "site/consultas/sm/cuentas/bonus/busqueda_filtros_bonus";
	public static final String MV_CONSULTAS_SM_BUSQUEDA_RESULTADO = "site/consultas/sm/busqueda_resultado";
	public static final String MV_CONSULTAS_SM_BUSQUEDA_RESULTADO_BONUS = "site/consultas/sm/cuentas/bonus/busqueda_resultado_bonus";
	public static final String MV_CONSULTAS_EDO_CTA_ACTIVACION_TERMCOND = "site/consultas/edocta/activacion/edocta_Activacion_TermCond";
	public static final String MV_CONSULTAS_EDO_CTA_ACTIVACION_CONFIRMA = "site/consultas/edocta/activacion/edocta_Activacion_Confirma";
	public static final String MV_CONSULTAS_EDO_CTA_ACTIVACION_COMPROBANTE = "site/consultas/edocta/activacion/edocta_Activacion_Comprobante";
	public static final String MV_CONSULTAS_EDO_CTA_CANCELACION_SELECCIONA = "site/consultas/edocta/cancelacion/edocta_Cancelacion_Selecciona";
	public static final String MV_CONSULTAS_EDO_CTA_CANCELACION_CONFIRMA = "site/consultas/edocta/cancelacion/edocta_Cancelacion_Confirma";
	public static final String MV_CONSULTAS_EDO_CTA_CANCELACION_COMPROBANTE = "site/consultas/edocta/cancelacion/edocta_Cancelacion_Comprobante";
	public static final String MV_CONSULTAS_EDO_CTA_EMISION_SELECCIONA = "site/consultas/edocta/emision/edocta_Emision_Selecciona";
	public static final String MV_CONSULTAS_EDO_CTA_EMISION_COMPROBANTE = "site/consultas/edocta/emision/edocta_Emision_Comprobante";
	//OJOS
	public static final String MV_CONSULTAS_SM_PRESTAMO = "site/consultas/sm/detalle_prestamo";
	public static final String MV_CONSULTAS_SM_INVERSION= "site/consultas/sm/detalle_inversion";
	public static final String MV_CONSULTAS_BONUS= "site/consultas/sm/resumenBonus";
	// #CONSULTAS----Historial de operaciones#//
	public static final String MV_CONSULTAS_HISTORIAL_BUSQUEDA = "site/consultas/historial/historial_busqueda";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS = "site/consultas/historial/historial_resultados";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_SERVICIOS = "site/consultas/historial/historial_resultados_servicios";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_ALERTAS = "site/consultas/historial/historial_resultados_alertas";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_ASOCIA = "site/consultas/historial/historial_resultados_asocia";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_CAMBIOESTATUSTDD = "site/consultas/historial/historial_resultados_cambioEstatusTDD";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_CAMBIOLIMTRANS = "site/consultas/historial/historial_resultados_cambioLimTransfer";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_EDOCUENTAESTATUS = "site/consultas/historial/historial_resultados_edoCtaEstatus";
	public static final String MV_CONSULTAS_HISTORIAL_RESULTADOS_CASHEXPRESS = "site/consultas/historial/historial_resultados_cashexpress";
	
	// #TRANSFERENCIAS----Cuentas Afirme - Cuentas Terceros#//
	public static final String MV_TRANSFERENCIAS_CTA_TERCEROS = "site/transferencias/terceros/terceros";
	public static final String MV_TRANSFERENCIAS_INGRESO_DATOS = "site/transferencias/terceros/terceros_datos";
	public static final String MV_TRANSFERENCIAS_CONFIRMACION = "site/transferencias/terceros/terceros_confirmacion";
	public static final String MV_TRANSFERENCIAS_COMPROBANTE = "site/transferencias/terceros/terceros_comprobante";
	public static final String MV_TRANSFERENCIAS_LISTA_GUARDADAS = "site/transferencias/terceros/terceros_lista";
	public static final String MV_TRANSFERENCIAS_LISTA_COMPROBANTES = "site/transferencias/terceros/terceros_listacomprobantes";
	public static final String MV_TRANSFERENCIAS_AGREGA_TRANSACCION = "/transferencias/terceros/agrega_transaccion";
	public static final String MV_CEP_SPEI = "site/transferencias/terceros/terceros_CEP_post";
	// #TRANSFERENCIAS----Cuentas Afirme - Cuentas Propias#//
	public static final String MV_TRANSFERENCIAS_PROPIAS_SELECCION = "site/transferencias/propias/propias_selecciona";
	public static final String MV_TRANSFERENCIAS_PROPIAS_CAPTURA = "site/transferencias/propias/propias_captura";
	public static final String MV_TRANSFERENCIAS_PROPIAS_CONFIRMAR = "site/transferencias/propias/propias_confirma";
	public static final String MV_TRANSFERENCIAS_PROPIAS_COMPROBANTE = "site/transferencias/propias/propias_comprobante";
	public static final String MV_TRANSFERENCIAS_PROPIAS_CONFGUARDADAS = "site/transferencias/propias/propias_guardadas";
	public static final String MV_TRANSFERENCIAS_PROPIAS_COMPFGUARDADAS = "site/transferencias/propias/propias_compguardadas";
	public static final String MV_TRANSFERENCIAS_PROPIAS_GUARDAR = "/transferencias/propias/guarda_propias.htm";
	// #TRANSFERENCIAS ---- Cuentas bonus
	public static final String MV_TRANSFERENCIAS_BONUS_SELECCION = "site/transferencias/bonus/bonus_selecciona";
	public static final String MV_TRANSFERENCIAS_BONUS_CAPTURA = "site/transferencias/bonus/bonus_captura";
	public static final String MV_TRANSFERENCIAS_BONUS_CONFIRMAR = "site/transferencias/bonus/bonus_confirma";
	public static final String MV_TRANSFERENCIAS_BONUS_COMPROBANTE = "site/transferencias/bonus/bonus_comprobante";
	// # TRANSFERENCIAS----Bancos Internacionales - Dolares Americanos #//
	public static final String MV_TRANSFERENCIAS_DOLARES = "site/transferencias/internacionales/dolares/dolares";
	public static final String MV_TRANSFERENCIAS_DOLARES_INGRESO_DATOS = "site/transferencias/internacionales/dolares/dolares_datos";
	public static final String MV_TRANSFERENCIAS_DOLARES_CONFIRMACION = "site/transferencias/internacionales/dolares/dolares_confirmacion";
	public static final String MV_TRANSFERENCIAS_DOLARES_COMPROBANTE = "site/transferencias/internacionales/dolares/dolares_comprobante";
	// # TRANSFERENCIAS----Bancos Internacionales - Multimoneda #//
	public static final String MV_TRANSFERENCIAS_MULTIMONEDA = "site/transferencias/internacionales/multimoneda/multimoneda";
	public static final String MV_TRANSFERENCIAS_MULTIMONEDA_INGRESO_DATOS = "site/transferencias/internacionales/multimoneda/multimoneda_datos";
	public static final String MV_TRANSFERENCIAS_MULTIMONEDA_CONFIRMACION = "site/transferencias/internacionales/multimoneda/multimoneda_confirmacion";
	public static final String MV_TRANSFERENCIAS_MULTIMONEDA_COMPROBANTE = "site/transferencias/internacionales/multimoneda/multimoneda_comprobante";
	// #TRANSFERENCIAS----Bancos Nacionaless#//
	public static final String MV_TRANSFERENCIAS_NACIONALES_SELECCION_FW = "transferencias/nacionales/bancos_nacionales.htm";
	public static final String MV_TRANSFERENCIAS_NACIONALES_SELECCION = "site/transferencias/nacionales/nacionales_selecciona";
	public static final String MV_TRANSFERENCIAS_NACIONALES_CAPTURA = "site/transferencias/nacionales/nacionales_captura";
	public static final String MV_TRANSFERENCIAS_NACIONALES_CONFIRMAR = "site/transferencias/nacionales/nacionales_confirma";
	public static final String MV_TRANSFERENCIAS_NACIONALES_COMPROBANTE = "site/transferencias/nacionales/nacionales_comprobante";
	public static final String MV_TRANSFERENCIAS_NACIONALES_LISTA_GUARDADAS = "site/transferencias/nacionales/nacionales_lista";
	public static final String MV_TRANSFERENCIAS_NACIONALES_LISTA_COMPROBANTES = "site/transferencias/nacionales/nacionales_listacomprobantes";
	public static final String MV_TRANSFERENCIAS_NACIONALES_AGREGA_TRANSACCION = "/transferencias/nacionales/nacionales_agrega_transaccion";

	// #TRANSFERENCIAS----Cuentas Afirme - Domingo electronico#//
	public static final String MV_DOMINGO_ELEC_CTAS = "site/transferencias/domingo/seleccion";
	public static final String MV_DOMINGO_ELEC_DATOS = "site/transferencias/domingo/datos";
	public static final String MV_DOMINGO_ELEC_CONFIRMACION = "site/transferencias/domingo/confirmacion";
	public static final String MV_DOMINGO_ELEC_COMPROBANTE = "site/transferencias/domingo/comprobante";
	public static final String MV_DOMINGO_ELEC_EJECUTAR = "/transferencias/domingo/ejecutar";
	public static final String MV_DOMINGO_CAN_SELECCION = "site/configuraciones/cancelaDomingo/selecciona";

	// #TRANSFERENCIAS----Cash Express#//
	public static final String MV_CASH_EXPRESS_EMITIR = "site/transferencias/cashExpress/cash_seleccion";
	public static final String MV_CASH_EXPRESS_EMITIR_CAPTURA = "site/transferencias/cashExpress/cash_captura";
	public static final String MV_CASH_EXPRESS_EMITIR_CONFIRMA = "site/transferencias/cashExpress/cash_confirma";
	public static final String MV_CASH_EXPRESS_EMITIR_COMPROBANTE = "site/transferencias/cashExpress/cash_comprobante";
	public static final String MV_CASH_EXPRESS_CANCELACION = "site/transferencias/cashExpress/cash_cancelacion";
	public static final String MV_CASH_EXPRESS_QUE_ES = "site/transferencias/cashExpress/cash_quees";

	public static final String MV_CASH_EXPRESS_BENE_LISTA = "site/transferencias/cashExpress/bene/bcash_lista";
	public static final String MV_CASH_EXPRESS_BENE_ALTA = "site/transferencias/cashExpress/bene/bcash_alta";
	public static final String MV_CASH_EXPRESS_BENE_ALTA_CONFIRMA = "site/transferencias/cashExpress/bene/bcash_confirma";
	public static final String MV_CASH_EXPRESS_BENE_ALTA_COMPROBANTE = "site/transferencias/cashExpress/bene/bcash_comprobante";

	public static final String MV_CASH_EXPRESS_HIST_BUSQUEDA = "site/transferencias/cashExpress/historico/hcash_busqueda";
	public static final String MV_CASH_EXPRESS_HIST_RESULTADO = "site/transferencias/cashExpress/historico/hcash_resultado";
	public static final String MV_CASH_EXPRESS_HIST_EDITAR = "site/transferencias/cashExpress/historico/hcash_editar";

	// #--------------FAVORITAS--------------# //
	public static final String MV_FAVORITAS_SELECCIONA = "site/transferencias/favoritas/seleccion";
	public static final String MV_FAVORITAS_CONFIRMA = "site/transferencias/favoritas/confirma";
	public static final String MV_FAVORITAS_EJECUTAR = "/transferencias/favoritas/ejecutar";
	public static final String MV_FAVORITAS_COMPROBANTE = "site/transferencias/favoritas/comprobante";
	public static final String MV_FAVORITAS_CONFIGURACION = "site/configuraciones/favoritas/favoritas";
	public static final String MV_FAVORITAS_ELIMINAR = "site/configuraciones/favoritas/eliminar";

	// #PAGOS--- TDC PROPIA S# //
	public static final String MV_PAGOS_TDCPROPIAS = "site/pagos/tdcPropias/tdc_selecciona";
	public static final String MV_PAGOS_TDCPROPIAS_CAPTURA = "site/pagos/tdcPropias/tdc_captura";
	public static final String MV_PAGOS_TDCPROPIAS_CONFIRMA = "site/pagos/tdcPropias/tdc_confirma";
	public static final String MV_PAGOS_TDCPROPIAS_COMPROBANTE = "site/pagos/tdcPropias/tdc_comprobante";

	// #PAGOS--- TDC TERCEROS, OTROS BANCOS, AMERICAN EXPRESS # //
	public static final String MV_PAGOS_TDC = "site/pagos/tdcPagos/tdc_selecciona";
	public static final String MV_PAGOS_TDC_CAPTURA = "site/pagos/tdcPagos/tdc_captura";
	public static final String MV_PAGOS_TDC_CONFIRMA = "site/pagos/tdcPagos/tdc_confirma";
	public static final String MV_PAGOS_TDC_COMPROBANTE = "site/pagos/tdcPagos/tdc_comprobante";

	// #PAGOS--- TDC DOMICILIAR # //
	public static final String MV_TDCDOMI = "site/tdc/domiciliacion/tdc_selecciona";
	public static final String MV_TDCDOMI_CONTRATO = "site/tdc/domiciliacion/tdc_contrato";
	public static final String MV_TDCDOMI_CONFIRMA = "site/tdc/domiciliacion/tdc_confirma";
	public static final String MV_TDCDOMI_COMPROBANTE = "site/tdc/domiciliacion/tdc_comprobante";
	public static final String MV_TDCDOMICANCELA = "site/tdc/domiciliacion/cancela_seleccion";
	public static final String MV_TDCDOMICANCELA_CONFIRMA = "site/tdc/domiciliacion/cancela_confirmacion";
	public static final String MV_TDCDOMICONSPAG_SELECCIONA = "site/tdc/domiciliacion/consPagos_Seleccion";
	public static final String MV_TDCDOMICONSPAG_PAGOS = "site/tdc/domiciliacion/consPagos_Pagos";

	// #PAGOS--- SEGUROS ANTIFRAUDES # //
	public static final String MV_PAGOS_SEGURO = "site/pagos/seguros/seg_selecciona";
	public static final String MV_PAGOS_SEGURO_PAGOS = "site/pagos/seguros/seg_mi_seguro";
	public static final String MV_PAGOS_SEGURO_CONTRATO = "site/pagos/seguros/seg_contrato";
	public static final String MV_PAGOS_SEGURO_CONFIRMA = "site/pagos/seguros/seg_confirma";
	public static final String MV_PAGOS_SEGURO_COMPROBANTE = "site/pagos/seguros/seg_comprobante";

	// # PAGOS --- IMPUESTOS GDF # //
	public static final String MV_PAGOS_IMPUESTOS_GDF = "site/pagos/gdf/gdf_seleccion";
	public static final String MV_PAGOS_IMPUESTOS_GDF_DATOS = "site/pagos/gdf/gdf_ingreso_datos";
	public static final String MV_PAGOS_IMPUESTOS_GDF_CONFIRMACION = "site/pagos/gdf/gdf_confirmacion";
	public static final String MV_PAGOS_IMPUESTOS_GDF_COMPROBANTE = "site/pagos/gdf/gdf_comprobante";

	// # PAGOS --- DOMICILIACION SERVICIOS # //
	public static final String MV_PAGOS_DOMICILIACION = "site/pagos/domiciliacion/domiciliacion_seleccion";
	public static final String MV_PAGOS_DOMICILIACION_CONTRATO = "site/pagos/domiciliacion/domiciliacion_contrato";
	public static final String MV_PAGOS_DOMICILIACION_DATOS = "site/pagos/domiciliacion/domiciliacion_datos";
	public static final String MV_PAGOS_DOMICILIACION_CONFIRMACION = "site/pagos/domiciliacion/domiciliacion_confirmacion";
	public static final String MV_PAGOS_DOMICILIACION_COMPROBANTE = "site/pagos/domiciliacion/domiciliacion_comprobante";
	public static final String MV_PAGOS_DOMICILIACION_HISTORICO = "site/pagos/domiciliacion/domiciliacion_historico";
	public static final String MV_PAGOS_DOMICILIACION_HISTORICO_DETALLE = "site/pagos/domiciliacion/domiciliacion_historico_detalle";
	public static final String MV_PAGOS_DOMICILIACION_EDICION = "site/pagos/domiciliacion/domiciliacion_edicion";
	public static final String MV_PAGOS_DOMICILIACION_CANCELACION = "site/pagos/domiciliacion/domiciliacion_cancelacion";

	// #--------------SERVICIOS--------------# //
	public static final String MV_SERVICIOS_SELECCION_RD = "servicios/seleccion";
	public static final String MV_SERVICIOS_SELECCION = "site/pagos/servicios/seleccion";
	public static final String MV_SERVICIOS_CONTRATO = "site/pagos/servicios/contrato";
	public static final String MV_SERVICIOS_DATOS = "site/pagos/servicios/datos";
	public static final String MV_SERVICIOS_ACTIVA_CONFIRMA = "site/pagos/servicios/activa_confirma";
	public static final String MV_SERVICIOS_ACTIVA_COMPROBANTE = "site/pagos/servicios/activa_comprobante";
	public static final String MV_SERVICIOS_CONFIRMA = "site/pagos/servicios/confirma";
	public static final String MV_SERVICIOS_COMPROBANTE = "site/pagos/servicios/comprobante";

	// #PAGOS--- SEGUROS AFIRME # //
	public static final String MV_PAGOS_SEGURO_AFIRME = "site/pagos/seguroAfirme/seg_afi_selecciona";
	public static final String MV_PAGOS_SEGURO_AFIRME_CAPTURA = "site/pagos/seguroAfirme/seg_afi_captura";
	public static final String MV_PAGOS_SEGURO_AFIRME_CONFIRMA = "site/pagos/seguroAfirme/seg_afi_confirma";
	public static final String MV_PAGOS_SEGURO_AFIRME_COMPROBANTE = "site/pagos/seguroAfirme/seg_afi_comprobante";

	// #PAGOS--- SERVICIOS --- SERVICIOS REFERENCIADO # //
	public static final String MV_PAGOS_SERVICIO_REFERENCIADO = "site/pagos/servicios/referenciado/servicios_selecciona";
	public static final String MV_PAGOS_SERVICIO_REFERENCIADO_CAPTURA = "site/pagos/servicios/referenciado/servicios_captura";
	public static final String MV_PAGOS_SERVICIO_REFERENCIADO_CONFIRMA = "site/pagos/servicios/referenciado/servicios_confirma";
	public static final String MV_PAGOS_SERVICIO_REFERENCIADO_COMPROBANTE = "site/pagos/servicios/referenciado/servicios_comprobante";

	// #PAGOS--- IMPUESTOS --- PAGO REFERENCIADO # //
	public static final String MV_PAGOS_IMPUESTOS_REFERENCIADO = "site/pagos/impuestos/referenciado/imp_ref_selecciona";
	// public static final String MV_PAGOS_IMPUESTOS_REFERENCIADO_CAPTURA =
	// "site/pagos/impuestos/referenciado/imp_ref_captura";
	public static final String MV_PAGOS_IMPUESTOS_REFERENCIADO_CONFIRMA = "site/pagos/impuestos/referenciado/imp_ref_confirma";
	public static final String MV_PAGOS_IMPUESTOS_REFERENCIADO_COMPROBANTE = "site/pagos/impuestos/referenciado/imp_ref_comprobante";

	// #--------------INVERSIONES--------------# //
	// #INVERSIONES--- DIARIA # //
	public static final String MV_INVERSIONES_DIARIA = "site/inversiones/diaria/diaria_selecciona";
	public static final String MV_INVERSIONES_DIARIA_CAPTURA = "site/inversiones/diaria/diaria_captura";
	public static final String MV_INVERSIONES_DIARIA_CONFIRMA = "site/inversiones/diaria/diaria_confirma";
	public static final String MV_INVERSIONES_DIARIA_COMPROBANTE = "site/inversiones/diaria/diaria_comprobante";

	// #INVERSIONES--- PERFECTA # //
	public static final String MV_INVERSIONES_PERFECTA = "site/inversiones/perfecta/perfecta_selecciona";
	public static final String MV_INVERSIONES_PERFECTA_CONFIRMA = "site/inversiones/perfecta/perfecta_confirma";
	public static final String MV_INVERSIONES_PERFECTA_COMPROBANTE = "site/inversiones/perfecta/perfecta_comprobante";

	// #PAGOS--- IMPUESTOS --- FEDERALES ANUALES# //
	public static final String MV_PAGOS_IMPUESTOS_FED_CAPTURA_RD = "captura";
	public static final String MV_PAGOS_IMPUESTOS_FED_CAPTURA = "site/pagos/impuestos/federales/captura";
	public static final String MV_PAGOS_IMPUESTOS_FED_CONFIRMA = "site/pagos/impuestos/federales/confirma";
	public static final String MV_PAGOS_IMPUESTOS_FED_COMPROBANTE = "site/pagos/impuestos/federales/comprobante";
	public static final String MV_PAGOS_IMPUESTOS_FED_REIMP_COMPROBANTE = "site/pagos/impuestos/federales/reimpresion";

	// # PAGARE AHORRAFIRME # //
	public static final String MV_PAGARE_AHORRAFIRME = "site/inversiones/pagare/ahorrafirme/pagare_ahorrafirme";
	public static final String MV_PAGARE_AHORRAFIRME_CONFIRMACION = "site/inversiones/pagare/ahorrafirme/pagare_confirmacion";
	public static final String MV_PAGARE_AHORRAFIRME_COMPROBANTE = "site/inversiones/pagare/ahorrafirme/pagare_comprobante";
	public static final String MV_PAGARE_AHORRAFIRME_CONSULTA = "site/inversiones/pagare/ahorrafirme/pagare_consulta";
	public static final String MV_PAGARE_AHORRAFIRME_EDICION_CONFIRMACION = "site/inversiones/pagare/ahorrafirme/pagare_editar_confirmacion";
	public static final String MV_PAGARE_AHORRAFIRME_EDICION_COMPROBANTE = "site/inversiones/pagare/ahorrafirme/pagare_editar_comprobante";

	// # PAGARE GRADUAL # //
	public static final String MV_PAGARE_GRADUAL = "site/inversiones/pagare/gradual/pagare_gradual";
	public static final String MV_PAGARE_GRADUAL_CONFIRMACION = "site/inversiones/pagare/gradual/pagare_confirmacion";
	public static final String MV_PAGARE_GRADUAL_COMPROBANTE = "site/inversiones/pagare/gradual/pagare_comprobante";
	public static final String MV_PAGARE_GRADUAL_CONSULTA = "site/inversiones/pagare/gradual/pagare_consulta";
	public static final String MV_PAGARE_GRADUAL_CANCELAR = "site/inversiones/pagare/gradual/pagare_cancelar_datos";
	public static final String MV_PAGARE_GRADUAL_CANCELAR_CONFIRMACION = "site/inversiones/pagare/gradual/pagare_cancelar_confirmacion";
	public static final String MV_PAGARE_GRADUAL_CANCELAR_COMPROBANTE = "site/inversiones/pagare/gradual/pagare_cancelar_comprobante";

	// # PAGARE MÚLTIPLE # //
	public static final String MV_PAGARE_MULTIPLE = "site/inversiones/pagare/multiple/pagare_multiple";
	public static final String MV_PAGARE_MULTIPLE_CONFIRMACION = "site/inversiones/pagare/multiple/pagare_confirmacion";
	public static final String MV_PAGARE_MULTIPLE_COMPROBANTE = "site/inversiones/pagare/multiple/pagare_comprobante";
	public static final String MV_PAGARE_MULTIPLE_CONSULTA = "site/inversiones/pagare/multiple/pagare_consulta";
	public static final String MV_PAGARE_MULTIPLE_CANCELAR = "site/inversiones/pagare/multiple/pagare_cancelar_datos";
	public static final String MV_PAGARE_MULTIPLE_CANCELAR_CONFIRMACION = "site/inversiones/pagare/multiple/pagare_cancelar_confirmacion";
	public static final String MV_PAGARE_MULTIPLE_CANCELAR_COMPROBANTE = "site/inversiones/pagare/multiple/pagare_cancelar_comprobante";
	
	// # FONDO AHORRO - COMPRA # //
	public static final String MV_FONDOS_COMPRA = "site/inversiones/fondos/compra/seleccion";
	public static final String MV_FONDOS_COMPRA_DATOS = "site/inversiones/fondos/compra/datos";
	public static final String MV_FONDOS_COMPRA_CALCULO = "site/inversiones/fondos/compra/calculo";
	public static final String MV_FONDOS_COMPRA_CONFIRMACION = "site/inversiones/fondos/compra/confirmacion";
	public static final String MV_FONDOS_COMPRA_COMPROBANTE = "site/inversiones/fondos/compra/comprobante";
	
	// # FONDO AHORRO - VENTA # //
	public static final String MV_FONDOS_VENTA = "site/inversiones/fondos/venta/seleccion";
	public static final String MV_FONDOS_VENTA_DATOS = "site/inversiones/fondos/venta/datos";
	public static final String MV_FONDOS_VENTA_CALCULO = "site/inversiones/fondos/venta/calculo";
	public static final String MV_FONDOS_VENTA_CONFIRMACION = "site/inversiones/fondos/venta/confirmacion";
	public static final String MV_FONDOS_VENTA_COMPROBANTE = "site/inversiones/fondos/venta/comprobante";

	// #--------------NOMINA--------------# //
	// # NOMINA # //
	public static final String MV_NOMINA = "site/nomina/nomina";
	public static final String MV_NOMINA_ANTICIPO = "site/nomina/nomina_anticipo";
	public static final String MV_NOMINA_DISPOSICION = "site/nomina/nomina_disposicion";
	public static final String MV_NOMINA_ABONO = "site/nomina/nomina_abono";
	public static final String MV_NOMINA_CONSULTA = "site/nomina/nomina_consulta";
	public static final String MV_NOMINA_CONFIRMACION = "site/nomina/nomina_confirmacion";
	public static final String MV_NOMINA_COMPROBANTE = "site/nomina/nomina_comprobante";
	public static final String TEST = "site/nomina/test";

	// #TDC--- CONSULTA SALDOS# //
	public static final String MV_SALDOS_TDC = "site/tdc/consultas/seleccion";
	public static final String MV_SALDOS_TDC_GENERAL = "site/tdc/consultas/general";
	public static final String MV_SALDOS_TDC_ADICIONAL = "site/tdc/consultas/adicional";
	public static final String MV_SALDOS_TDC_MOVIMIENTOS = "site/tdc/consultas/movimientos";
	public static final String MV_SALDOS_TDC_PROMOCIONES = "site/tdc/consultas/promociones";

	// #--------------NOMINA--------------# //
	public static final String MV_AFIRMEMOVIL_CONTRATO = "site/movil/movil_contrato";
	public static final String MV_AFIRMEMOVIL_ACTIVACION = "site/movil/movil_activacion";
	public static final String MV_AFIRMEMOVIL_LIMITES = "site/movil/movil_limites";
	public static final String MV_AFIRMEMOVIL_CONFIRMACION = "site/movil/movil_confirmacion";
	public static final String MV_AFIRMEMOVIL_COMPROBANTE = "site/movil/movil_comprobante";
	public static final String MV_AFIRMEMOVIL_CANCELACION = "site/movil/movil_cancelacion";
	public static final String MV_AFIRMEMOVIL_CONSULTA = "site/movil/movil_consulta";
	public static final String MV_AFIRMEMOVIL_HISTORICO = "site/movil/movil_historico";
	public static final String MV_AFIRMEMOVIL_DETALLE = "site/movil/movil_detalle";

	// #--------------ACTIVACION TDC--------------# //
	public static final String MV_TDCACTIVA_SELECCION = "site/tdc/activacion/seleccion";
	public static final String MV_TDCACTIVA_CONFIRMA = "site/tdc/activacion/confirmacion";
	public static final String MV_TDCACTIVA_COMPROBANTE = "site/tdc/activacion/comprobante";

	// #--------------LIMITES TDC--------------# //
	public static final String MV_TDCLIMITE_SELECCION = "site/tdc/limites/seleccion";
	public static final String MV_TDCLIMITE_DATOS = "site/tdc/limites/datos";
	public static final String MV_TDCLIMITE_CONFIRMA = "site/tdc/limites/confirmacion";
	public static final String MV_TDCLIMITE_COMPROBANTE = "site/tdc/limites/comprobante";

	// #--------------DISPOSICION TDC--------------# //
	public static final String MV_TDCDISP_SELECCION = "site/tdc/disposicion/seleccion";
	public static final String MV_TDCDISP_DATOS = "site/tdc/disposicion/datos";
	public static final String MV_TDCDISP_CONFIRMA = "site/tdc/disposicion/confirmacion";
	public static final String MV_TDCDISP_COMPROBANTE = "site/tdc/disposicion/comprobante";

	// #CONFIGURACIONES--- CUENTAS TERCEROS # //
	public static final String MV_CTALTAGENERAL = "site/configuraciones/cuentasTerceros/alta_general";
	public static final String MV_CTALTA = "site/configuraciones/cuentasTerceros/alta_cuenta";
	public static final String MV_CTALTA_CONFIRMA = "site/configuraciones/cuentasTerceros/alta_cuenta_confirmacion";
	public static final String MV_CTALTA_ACTIVA = "site/configuraciones/cuentasTerceros/alta_cuenta_activacion";
	public static final String MV_CTALTA_COMPROBANTE = "site/configuraciones/cuentasTerceros/alta_cuenta_comprobante";
	public static final String MV_CTLISTA_CUENTAS = "site/configuraciones/cuentasTerceros/admin_cuenta_lista";
	public static final String MV_CTADMIN_CONFIRMA = "site/configuraciones/cuentasTerceros/admin_cuenta_confirma";
	public static final String MV_CTADMIN_COMPROBANTE = "site/configuraciones/cuentasTerceros/admin_cuenta_comprobante";
	public static final String MV_CTALTATARJ = "site/configuraciones/cuentasTerceros/alta_tarjeta";
	public static final String MV_CTALTATARJ_CONFIRMA = "site/configuraciones/cuentasTerceros/alta_tarjeta_confirmacion";
	public static final String MV_CTALTATARJ_COMPROBANTE = "site/configuraciones/cuentasTerceros/alta_tarjeta_comprobante";
	
	public static final String MV_CT_ELIMINAR_CUENTA = "site/configuraciones/cuentasTerceros/eliminar/eliminar_cuenta";
	public static final String MV_CT_ELIMINAR_CUENTA_CONFIRMAR = "site/configuraciones/cuentasTerceros/eliminar/eliminar_cuenta_confirmar";
	public static final String MV_CT_ELIMINAR_CUENTA_COMPROBANTE = "site/configuraciones/cuentasTerceros/eliminar/eliminar_cuenta_comprobante";
	
	// # CONFIGURACIONES --- CUENTAS INTERNACIONALES # //
	public static final String MV_CTA_INT_DATOS = "site/configuraciones/cuentasInternacionales/alta/alta_datos";
	public static final String MV_CTA_INT_ALTA = "site/configuraciones/cuentasInternacionales/alta/alta_cuenta";
	public static final String MV_CTA_INT_ACTIVACION = "site/configuraciones/cuentasInternacionales/alta/alta_activacion";
	public static final String MV_CTA_INT_COMPROBANTE = "site/configuraciones/cuentasInternacionales/alta/alta_comprobante";

	// # NOMINA --- PORTABILIDAD # //
	public static final String MV_MODAL_PORTABILIDAD = "site/nomina/modal_portabilidad";
	public static final String MV_PORTABILIDAD = "site/nomina/portabilidad";
	public static final String MV_PORTABILIDAD_OTRO = "site/nomina/portabilidad_otro";
	public static final String MV_PORTABILIDAD_CANCELA = "site/nomina/portabilidad_cancela";
	public static final String MV_PORTABILIDAD_CONFIRMACION = "site/nomina/portabilidad_confirmacion";
	public static final String MV_PORTABILIDAD_COMPROBANTE = "site/nomina/portabilidad_comprobante";

	// ### Termina definicion de Paths ###//

	public static final String USUARIO_SESSION = "afirmeNetUser";
	public static final String CUENTAS_SESSION = "cuentas";
	public static final String CREDITOS_SESSION = "creditos";
	public static final String TIEMPO_CUENTAS_SESSION = "tiempoEsperaCuentas";
	public static final String INVERSIONES_SESSION = "inversiones";
	public static final String BACKGROUD_IMAGE = "backgroudImg";
	public static final String BACKGROUD_PATH_IMAGE = "/resources/img/stages/stage.jpg";
	public static final String BACKGROUD_PATRIMONIAL_PATH_IMAGE = "/resources/img/stages/stagePatrimonial.jpg";

	// #CONFIGURACIONES# //
	public static final String MV_CONFIGURACIONES = "site/configuraciones/configuraciones";

	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASAFIRME = "site/configuraciones/limitesTransferecias/cuentas_Afirme";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASAFIRME_CAPTURALIMITES = "site/configuraciones/limitesTransferecias/cuentas_Afirme_Captura_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASAFIRME_CONFIRMALIMITES = "site/configuraciones/limitesTransferecias/cuentas_Afirme_Confirma_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASAFIRME_COMPROBANTELIMITES = "site/configuraciones/limitesTransferecias/cuentas_Afirme_Comprobante_Limites";

	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASOTRSBANCOS = "site/configuraciones/limitesTransferecias/cuentas_OtrsBancos";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASOTRSBANCOS_CAPTURALIMITES = "site/configuraciones/limitesTransferecias/cuentas_OtrsBancos_Captura_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASOTRSBANCOS_CONFIRMALIMITES = "site/configuraciones/limitesTransferecias/cuentas_OtrsBancos_Confirma_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASOTRSBANCOS_COMPROBANTELIMITES = "site/configuraciones/limitesTransferecias/cuentas_OtrsBancos_Comprobante_Limites";

	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASSERVICIOS = "site/configuraciones/limitesTransferecias/cuentas_Servicios";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASSERVICIOS_CAPTURALIMITES = "site/configuraciones/limitesTransferecias/cuentas_Servicios_Captura_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASSERVICIOS_CONFIRMALIMITES = "site/configuraciones/limitesTransferecias/cuentas_Servicios_Confirma_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASSERVICIOS_COMPROBANTELIMITES = "site/configuraciones/limitesTransferecias/cuentas_Servicios_Comprobante_Limites";

	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASTDC = "site/configuraciones/limitesTransferecias/cuentas_TDC";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASTDC_CAPTURALIMITES = "site/configuraciones/limitesTransferecias/cuentas_TDC_Captura_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASTDC_CONFIRMALIMITES = "site/configuraciones/limitesTransferecias/cuentas_TDC_Confirma_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASTDC_COMPROBANTELIMITES = "site/configuraciones/limitesTransferecias/cuentas_TDC_Comprobante_Limites";

	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASPAGOIMPUESTOS = "site/configuraciones/limitesTransferecias/cuentas_PagoImpuestos";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASPAGOIMPUESTOS_CAPTURALIMITES = "site/configuraciones/limitesTransferecias/cuentas_PagoImpuestos_Captura_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASPAGOIMPUESTOS_CONFIRMALIMITES = "site/configuraciones/limitesTransferecias/cuentas_PagoImpuestos_Confirma_Limites";
	public static final String MV_CONFIGURACIONES_LIMITES_CUENTASPAGOIMPUESTOS_COMPROBANTELIMITES = "site/configuraciones/limitesTransferecias/cuentas_PagoImpuestos_Comprobante_Limites";

	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOALIAS = "site/configuraciones/seguridad/alias/conf_Cambio_Alias";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOAVATAR = "site/configuraciones/seguridad/alias/conf_Cambio_Avatar";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOALIASAVATAR_CONFIRMAR = "site/configuraciones/seguridad/alias/conf_Cambio_Alias_Avatar_Confirmar";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOALIASAVATAR_COMPROBANTE = "site/configuraciones/seguridad/alias/conf_Cambio_Alias_Avatar_Comprobante";

	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO = "site/configuraciones/seguridad/correo/conf_Cambio_Correo_Electronico";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO_CONFIRMAR = "site/configuraciones/seguridad/correo/conf_Cambio_Correo_Electronico_Confirmar";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO_COMPROBANTE = "site/configuraciones/seguridad/correo/conf_Cambio_Correo_Electronico_Comprobante";

	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCONTRASENA = "site/configuraciones/seguridad/contrasena/conf_Cambio_Contrasena";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCONTRASENA_CONFIRMAR = "site/configuraciones/seguridad/contrasena/conf_Cambio_Contrasena_Confirmar";
	public static final String MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCONTRASENA_COMPROBANTE = "site/configuraciones/seguridad/contrasena/conf_Cambio_Contrasena_Comprobante";

	// #SERVICIOS# //
	public static final String MV_SERVICIOS = "site/servicios/servicios";

	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOESTATUS = "site/servicios/tarjetaDebito/cambio_Estatus";
	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOESTATUS_CAPTURAESTATUS = "site/servicios/tarjetaDebito/cambio_Estatus_Captura";
	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOESTATUS_CONFIRMAESTATUS = "site/servicios/tarjetaDebito/cambio_Estatus_Confirma";
	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOESTATUS_COMPROBANTEESTATUS = "site/servicios/tarjetaDebito/cambio_Estatus_Comprobante";

	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOLIMITES = "site/servicios/tarjetaDebito/cambio_Limites";
	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOLIMITES_CAPTURA = "site/servicios/tarjetaDebito/cambio_Limites_Captura";
	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOLIMITES_CONFIRMA = "site/servicios/tarjetaDebito/cambio_Limites_Confirma";
	public static final String MV_SERVICIOS_TARJETADEBITO_CAMBIOLIMITES_COMPROBANTE = "site/servicios/tarjetaDebito/cambio_Limites_Comprobante";
	
	public static final String MV_SERVICIOS_MOVIL_ASOCIA = "site/servicios/asociaMovilCuenta/asocia_Movil_Cuenta";
	public static final String MV_SERVICIOS_MOVIL_ASOCIA_ACTIVA = "site/servicios/asociaMovilCuenta/asocia_Movil_Cuenta_Activa";
	public static final String MV_SERVICIOS_MOVIL_ASOCIA_DATOS = "site/servicios/asociaMovilCuenta/asocia_Movil_Cuenta_Datos";
	public static final String MV_SERVICIOS_MOVIL_ASOCIA_ASOCIA = "site/servicios/asociaMovilCuenta/asocia_Movil_Cuenta_Asocia";
	public static final String MV_SERVICIOS_MOVIL_ASOCIA_COMPROBANTE = "site/servicios/asociaMovilCuenta/asocia_Movil_Cuenta_Comprobante";
	
	public static final String MV_SERVICIOS_MOVIL_MODIFICA_ASOCIA = "site/servicios/modificaAsoMovilCuenta/modifica_Movil_Cuenta";
	public static final String MV_SERVICIOS_MOVIL_MODIFICA_ASOCIA_DATOS = "site/servicios/modificaAsoMovilCuenta/modifica_Movil_Cuenta_Datos";
	public static final String MV_SERVICIOS_MOVIL_MODIFICA_ASOCIA_ASOCIA = "site/servicios/modificaAsoMovilCuenta/modifica_Movil_Cuenta_Asocia";
	public static final String MV_SERVICIOS_MOVIL_MODIFICA_ASOCIA_COMPROBANTE = "site/servicios/modificaAsoMovilCuenta/modifica_Movil_Cuenta_Comprobante";
	
	public static final String MV_SERVICIOS_MOVIL_DESASOCIA = "site/servicios/desAsociaMovilCuenta/desAsocia_Movil_Cuenta";
	public static final String MV_SERVICIOS_MOVIL_DESASOCIA_DATOS = "site/servicios/desAsociaMovilCuenta/desAsocia_Movil_Cuenta_Datos";
	public static final String MV_SERVICIOS_MOVIL_DESASOCIA_COMPROBANTE = "site/servicios/desAsociaMovilCuenta/desAsocia_Movil_Cuenta_Comprobante";

	public static final String MV_OPERACIONES_PROGRAMADAS = "site/configuraciones/programadas/selecciona";
	public static final String MV_OPERACIONES_PROGRAMADAS_CONFIRMA = "site/configuraciones/programadas/confirma";
	public static final String MV_OPERACIONES_PROGRAMADAS_COMPROBANTE = "site/configuraciones/programadas/comprobante";	
	
	public static final String MV_SERVICIOS_ALERTAS_TERMCOND = "site/servicios/alertas/alertas_Terminos_Condiciones";
	public static final String MV_SERVICIOS_ALERTAS_MEDIOS = "site/servicios/alertas/alertas_Medios";
	public static final String MV_SERVICIOS_ALERTAS_CONFIGURA_CUENTAS = "site/servicios/alertas/alertas_Configura_Cuentas";
	public static final String MV_SERVICIOS_ALERTAS_CONFIGURA_TDC = "site/servicios/alertas/alertas_Configura_TDC";
	public static final String MV_SERVICIOS_ALERTAS_CONFIGURA_INVERSIONES = "site/servicios/alertas/alertas_Configura_Inversiones";
	public static final String MV_SERVICIOS_ALERTAS_CONFIGURA_CREDITTOS = "site/servicios/alertas/alertas_Configura_Creditos";
	public static final String MV_SERVICIOS_ALERTAS_CONFIRMAR = "site/servicios/alertas/alertas_Confirma";
	public static final String MV_SERVICIOS_ALERTAS_COMPROBANTE = "site/servicios/alertas/alertas_Comprobante";

	public static final String MV_SERVICIOS_TARJETAS_AVISO_DE_VIAJE = "site/servicios/tarjetas/avisoViaje/AvisoDeViaje";
	public static final String MV_SERVICIOS_TARJETAS_AVISO_DE_VIAJE_CONFIRMAR = "site/servicios/tarjetas/avisoViaje/AvisoDeViajeConfirmar";
	public static final String MV_SERVICIOS_TARJETAS_AVISO_DE_VIAJE_COMPROBANTE = "site/servicios/tarjetas/avisoViaje/AvisoDeViajeComprobante";
	
	// #CHEQUERA# //
	public static final String MV_SOLICITUD_CHEQUERA_SELECCION_CUENTA = "site/configuraciones/chequera/solicitarChequera";
	public static final String MV_SOLICITUD_CHEQUERA_CONFIRMAR_CUENTA = "site/configuraciones/chequera/confirmarChequera";
	public static final String MV_SOLICITUD_CHEQUERA_COMPROBANTE_CUENTA = "site/configuraciones/chequera/comprobanteChequera";

	// #CONSULTA CHEQUE#//
	public static final String MV_CONSULTA_CHEQUE_SELECCION_CUENTA = "site/configuraciones/chequera/consulta/datosCheque";
	public static final String MV_CONSULTA_CHEQUE_RESULTADOS = "site/configuraciones/chequera/consulta/seleccionarCheque";

	// #CANCELAR CHEQUE#//
	public static final String MV_CANCELAR_CHEQUE_SELECCION_CUENTA = "site/configuraciones/chequera/cancelar/datosCheque";
	public static final String MV_CANCELAR_CHEQUE_RESULTADOS = "site/configuraciones/chequera/cancelar/seleccionarCheque";
	public static final String MV_CANCELAR_CHEQUE_COMPROBANTE = "site/configuraciones/chequera/cancelar/comprobante";

	// #PROTECCION CHEQUE#//
	public static final String MV_PROTECCION_CHEQUE_SELECCION_CUENTA = "site/configuraciones/chequera/proteccion/datosCheque";
	public static final String MV_PROTECCION_CHEQUE_CONFIRMACION = "site/configuraciones/chequera/proteccion/confirmacion";
	public static final String MV_PROTECCION_CHEQUE_COMPROBANTE = "site/configuraciones/chequera/proteccion/comprobante";


	// #MIS CREDITOS#//
	public static final String MV_MIS_CREDITOS_ABONO = "site/mis_creditos/mis_creditos_abono";
	public static final String MV_MIS_CREDITOS_CONFIRMA = "site/mis_creditos/mis_creditos_confirma";
	public static final String MV_MIS_CREDITOS_COMPROBANTE = "site/mis_creditos/mis_creditos_comprobante";
	
	// #RECARGAS TIEMPO AIRE#//
	public static final String MV_RECARGAS = "site/servicios/recargas/recargas";
	public static final String MV_RECARGAS_CONFIRMA = "site/servicios/recargas/recargas_confirma";
	public static final String MV_RECARGAS_COMPROBANTE = "site/servicios/recargas/recargas_comprobante";
	
	// ### Constantes transferencias ###//
	public static final List<String> CUENTAS_PROPIAS = Arrays.asList("DDA",
			"SAV", "NOW");
	// ### Constantes transferencias ###//
	//Cuentas sobre las cuales se pueden hacer inversiones
	public static final List<String> CUENTAS_INVERSIONES_PROPIAS = Arrays
			.asList("DDA", "SAV", "NOW", "MMK");
	// ### Constantes transferencias ###//
	public static final List<String> CUENTAS_CARDIF_NO_VALIDOS = Arrays
			.asList("BON", "MMK", "INS", "TDS", "CDS", "PLS", "PLP", "CBU", "MES", "LCR", "CLT");
	// ### Constantes transferencias ###//
	public static final List<String> PRODUCTOS_CARDIF_NO_VALIDOS = Arrays.asList("ACOM");

	public static final String IMPRIMIR = "imprimirPDF";

}
