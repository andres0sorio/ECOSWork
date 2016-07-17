class Tramite(object):
    def __init__(self):
        """clase basica de un tramite"""
        self.codigo         = "TX"
        self.nombre         = "AX"
        self.url            = "https"
        self.municipio      = "MX"
        self.grupo          = "GX"
        self.requerimientos = ["Y1"]
        self.pasos          = ["X1"]
        self.resultados     = ["X1"]
        self.verificacion   = ["X1"]
        
    def setCodigo(self, codigo):
        self.codigo = codigo

    def setNombre(self, nombre):
        self.nombre = nombre
            
    def setUrl(self, url):
        self.url = url

    def setMunicipio(self, municipio):
        self.municipio = municipio

    def setGrupo(self, grupo):
        self.grupo = grupo
        
    def setRequerimientos(self, reqs):
        self.requerimientos = reqs

    def setPasos(self, pasos):
        self.pasos = pasos

    def setResultados(self, resultados):
        self.resultados = resultados

    def setVerificacion(self, verificacion):
        self.verificacion = verificacion

    def getUrl(self):
        return self.url


