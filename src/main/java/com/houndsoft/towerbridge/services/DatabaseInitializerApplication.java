package com.houndsoft.towerbridge.services;

import com.houndsoft.towerbridge.services.model.*;
import com.houndsoft.towerbridge.services.repository.*;
import com.houndsoft.towerbridge.services.utils.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

//@SpringBootApplication
public class DatabaseInitializerApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializerApplication.class);

    @Autowired
    private ContactoRepository contactoRepository;
    @Autowired
    private PadreRepository padreRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ClaseRepository claseRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private CajaRepository cajaRepository;
    @Autowired
    private DescuentoRepository descuentoRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseInitializerApplication.class, args);
    }

    protected void initializeDb() {


        Descuento descuento = Builder.build(Descuento.class)
                .with(p -> p.setActivo(true))
                .with(p -> p.setDescripcion("Equivale al 10% de descuento para 1 de 2 hermanos anotados (aplica a aquel de menor monto de los dos)"))
                .with(p -> p.setNombre("2 Hermanos 10%"))
                .with(p -> p.setPorcentaje(10))
                .get();

        descuentoRepository.save(descuento);

//        alumnoRepository.save(alumno1);
//        alumnoRepository.save(alumno2);

        Usuario usuario = new Usuario();
        usuario.setPassword("myPassword");
        usuario.setPerfil(Usuario.Perfil.ADMIN);
        usuario.setUsername("admin");
        usuario.setEmail("tower.admin@gmail.com");
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setPassword("myPassword");
        usuario2.setPerfil(Usuario.Perfil.PROFESOR);
        usuario2.setUsername("mayra.teacher");
        usuario2.setEmail("mayra.teacher@gmail.com");
        usuarioRepository.save(usuario2);

        Contacto contacto1 =
                Builder.build(Contacto.class)
                        .with(p -> p.setDomicilio("Pte Peron 2020"))
                        .with(p -> p.setEmail("jlopez@gmail.com"))
                        .with(p -> p.setTelefono("2103021"))
                        .get();
//        contactoRepository.save(contacto1);

        Contacto contacto2 =
                Builder.build(Contacto.class)
                        .with(p -> p.setDomicilio("Urquiza 1278"))
                        .with(p -> p.setEmail("adri.fern@gmail.com"))
                        .with(p -> p.setTelefono("2237646"))
                        .get();
//        contactoRepository.save(contacto2);

        Contacto contacto3 =
                Builder.build(Contacto.class)
                        .with(p -> p.setDomicilio("Urquiza 1278"))
                        .with(p -> p.setEmail("jorge.fndz@gmail.com"))
                        .with(p -> p.setTelefono("3203948"))
                        .get();
//        contactoRepository.save(contacto3);

        Contacto contacto4 = new Contacto("domicilio prueba", "0239393", "mayra.teacher@gmail.com");


        Padre padre1 = new Padre("Javier Lopez", 23000992, contacto1,List.of(), "solo por la tarde");
        Padre padre2 = new Padre("Adriana Fernandez", 30479942,contacto2,List.of(), null);
        Padre padre3 = new Padre("Jorge Fernandez", 29076984, contacto3,List.of(), null);
        padreRepository.save(padre1);
//        padreRepository.save(padre2);
//        padreRepository.save(padre3);


        List<Padre> padresACargo = List.of(padre2, padre3);

        Alumno alumno1 =
                Builder.build(Alumno.class)
                        .with(p -> p.setNombreApellido("Tomas Fernandez"))
                        .with(p -> p.setAnioEscolar("2ro primaria"))
                        .with(p -> p.setColegio("Instituto Jose C Paz"))
                        .with(p -> p.setDni(60576987))
                        .with(p -> p.setFechaDeNacimiento("20/05/2013"))
                        .with(p -> p.setInstitucionesPrevias("primera vez en un instituto de ingles"))
                        .with(p -> p.setNivelIngles("Acorde a lo enseñado en el colegio"))
                        .with(p -> p.setRindeExamen(false))
                        .with(p -> p.setPadresACargo(padresACargo))
                        .with(p -> p.setDescuento(descuento))
                        .get();

        Alumno alumno2 =
                Builder.build(Alumno.class)
                        .with(p -> p.setNombreApellido("Tobias Fernandez"))
                        .with(p -> p.setAnioEscolar("2do grado"))
                        .with(p -> p.setColegio("Parroquia San Jose Obrero"))
                        .with(p -> p.setDni(60587936))
                        .with(p -> p.setFechaDeNacimiento("28/07/2014"))
                        .with(p -> p.setInstitucionesPrevias("primera vez en un instituto de ingles"))
                        .with(p -> p.setNivelIngles("Acorde a lo enseñado en el colegio"))
                        .with(p -> p.setRindeExamen(false))
                        .with(p -> p.setPadresACargo(padresACargo))
                        .get();

        Profesor profesor = new Profesor();
        profesor.setCbuCvu("algo.algomas.mp");
        profesor.setValorHoraDiferenciado(false);
        profesor.setContacto(contacto4);
        profesor.setDni(2334343);
        profesor.setFechaDeNacimiento("10/08/1076");
        profesor.setExperienciaPrevia("instituto britanico");
        profesor.setNombreApellido("Mayra Gonzalez");
        profesor.setUsuario(usuario2);
        profesorRepository.save(profesor);

        // Cambridge
        final Curso starters = Curso.builder().nombre("Starters").valorHoraProfesor(200).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(1500).build();
        cursoRepository.save(starters);
        cursoRepository.save(Curso.builder().nombre("Movers").valorHoraProfesor(200).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(1500).build());
        cursoRepository.save(Curso.builder().nombre("Flyers").valorHoraProfesor(250).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(2000).build());
        cursoRepository.save(Curso.builder().nombre("KET").valorHoraProfesor(350).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(2000).build());
        cursoRepository.save(Curso.builder().nombre("PET").valorHoraProfesor(350).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(2000).build());
        cursoRepository.save(Curso.builder().nombre("PRE-FCE").valorHoraProfesor(500).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(2300).build());
        final Curso fce = Curso.builder().nombre("FCE").valorHoraProfesor(600).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(3000).build();
        cursoRepository.save(fce);
        cursoRepository.save(Curso.builder().nombre("CAE").valorHoraProfesor(800).tipoDeCurso(Curso.TipoDeCurso.CAMBRIDGE_INTERNATIONAL).valorArancel(3000).build());

        // Adultos
        cursoRepository.save(Curso.builder().nombre("Basic").valorHoraProfesor(350).tipoDeCurso(Curso.TipoDeCurso.ADULTOS).valorArancel(2000).build());
        cursoRepository.save(Curso.builder().nombre("Elemental").valorHoraProfesor(500).tipoDeCurso(Curso.TipoDeCurso.ADULTOS).valorArancel(2300).build());
        cursoRepository.save(Curso.builder().nombre("Pre-Intermediate").valorHoraProfesor(600).tipoDeCurso(Curso.TipoDeCurso.ADULTOS).valorArancel(2800).build());
        final Curso intermediate = Curso.builder().nombre("Intermediate").valorHoraProfesor(800).tipoDeCurso(Curso.TipoDeCurso.ADULTOS).valorArancel(3000).build();
        cursoRepository.save(intermediate);
        cursoRepository.save(Curso.builder().nombre("Superior").valorHoraProfesor(800).tipoDeCurso(Curso.TipoDeCurso.ADULTOS).valorArancel(3000).build());

        // Especificos
        cursoRepository.save(Curso.builder().nombre("Travel").valorHoraProfesor(400).tipoDeCurso(Curso.TipoDeCurso.ESPECIFICOS).valorArancel(2000).build());
        final Curso business = Curso.builder().nombre("Business").valorHoraProfesor(400).tipoDeCurso(Curso.TipoDeCurso.ESPECIFICOS).valorArancel(2300).build();
        cursoRepository.save(business);


        final Clase starters1A = Clase.builder().dia(Clase.Dia.LUNES).curso(starters).nombre("Starters 1A").profesor(profesor)
                .horario("18 a 20 hs").claveClassroom("password123").claveVideollamada("call123").linkClassroom("Link al clasroom")
                .linkVideollamada("link a la videoLlamada").alumnosAnotados(List.of(alumno1,alumno2)).build();
        claseRepository.save(starters1A);
        claseRepository.save(Clase.builder().dia(Clase.Dia.MARTES).curso(fce).nombre("FCE A").profesor(profesor)
                .horario("19 a 21 hs").claveClassroom("password123").claveVideollamada("call123").linkClassroom("Link al clasroom")
                .linkVideollamada("link a la videoLlamada").build());

        claseRepository.save(Clase.builder().dia(Clase.Dia.VIERNES).curso(intermediate).nombre("Intermediate B").profesor(profesor)
                .horario("17 a 19 hs").claveClassroom("password123").claveVideollamada("call123").linkClassroom("Link al clasroom")
                .linkVideollamada("link a la videoLlamada").build());

        claseRepository.save(Clase.builder().dia(Clase.Dia.SABADO).curso(intermediate).nombre("Business Sábado!").profesor(profesor)
                .horario("9 a 11 hs").claveClassroom("password123").claveVideollamada("call123").linkClassroom("Link al clasroom")
                .linkVideollamada("link a la videoLlamada").build());

        Movimiento movimiento =
                Builder.build(Movimiento.class)
                        .with(p -> p.setDetalle("Primer movimiento. Ingreso inicial de dinero"))
                        .with(p -> p.setFecha(new Date()))
                        .with(p -> p.setMedioDePago(Movimiento.MedioDePago.EFECTIVO))
                        .with(p -> p.setMonto(10000))
                        .with(p -> p.setTipoMovimiento(Movimiento.TipoDeMovimiento.COBRO))
                        .with(p -> p.setUsuario(usuario))
                        .get();

        movimientoRepository.save(movimiento);

        Caja caja = Builder.build(Caja.class)
                .with(p -> p.setUltimoMovimiento(movimiento))
                .with(p -> p.setValorActual(movimiento.getMonto()))
                .get();

        cajaRepository.save(caja);
    }

    @Override
    public void run(String... args) throws Exception {
        initializeDb();
    }
}
