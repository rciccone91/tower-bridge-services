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
    @Autowired private PadreRepository padreRepository;
    @Autowired private AlumnoRepository alumnoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private MovimientoRepository movimientoRepository;
    @Autowired private CajaRepository cajaRepository;
    @Autowired private DescuentoRepository descuentoRepository;
    @Autowired private ProfesorRepository profesorRepository;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseInitializerApplication.class, args);
    }

    protected void initializeDb() {

        Contacto contacto1 =
                Builder.build(Contacto.class)
                        .with(p -> p.setDomicilio("Pte Peron 2020"))
                        .with(p -> p.setEmail("jlopez@gmail.com"))
                        .with(p -> p.setTelefono("2103021"))
                        .get();
        contactoRepository.save(contacto1);

        Contacto contacto2 =
                Builder.build(Contacto.class)
                        .with(p -> p.setDomicilio("Urquiza 1278"))
                        .with(p -> p.setEmail("adri.fern@gmail.com"))
                        .with(p -> p.setTelefono("2237646"))
                        .get();
        contactoRepository.save(contacto2);

        Contacto contacto3 =
                Builder.build(Contacto.class)
                        .with(p -> p.setDomicilio("Urquiza 1278"))
                        .with(p -> p.setEmail("jorge.fndz@gmail.com"))
                        .with(p -> p.setTelefono("3203948"))
                        .get();
        contactoRepository.save(contacto3);

//        Contacto contacto4 =
//                Builder.build(Contacto.class)
//                        .with(p -> p.setDomicilio("Lavall 1500"))
//                        .with(p -> p.setEmail("mayra.teacher@gmail.com"))
//                        .with(p -> p.setTelefono("938373438"))
//                        .get();
//        contactoRepository.save(contacto4);

        Padre padre1 = new Padre("Javier Lopez", 23000992, contacto1, "solo por la tarde");
        Padre padre2 = new Padre("Adriana Fernandez", 30479942, contacto2, null);
        Padre padre3 = new Padre("Jorge Fernandez", 29076984, contacto3, null);
        padreRepository.save(padre1);
        padreRepository.save(padre2);
        padreRepository.save(padre3);

        List<Padre> padresACargo = List.of(padre2, padre3);

        Descuento descuento =  Builder.build(Descuento.class)
                .with(p -> p.setActivo(true))
                .with(p -> p.setDescripcion("Equivale al 10% de descuento para 1 de 2 hermanos anotados (aplica a aquel de menor monto de los dos)"))
                .with(p -> p.setNombre("2 Hermanos 10%"))
                .with(p -> p.setPorcentaje(10))
                .get();

        descuentoRepository.save(descuento);

        Alumno alumno1 =
                Builder.build(Alumno.class)
                        .with(p -> p.setNombreApellido("Tomas Fernandez"))
                        .with(p -> p.setAnioEscolar("2do grado"))
                        .with(p -> p.setColegio("Instituto Jose C Paz"))
                        .with(p -> p.setDni(60576987))
                        .with(p -> p.setEdad(7))
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
                        .with(p -> p.setColegio("Instituto Jose C Paz"))
                        .with(p -> p.setDni(60587936))
                        .with(p -> p.setEdad(7))
                        .with(p -> p.setInstitucionesPrevias("primera vez en un instituto de ingles"))
                        .with(p -> p.setNivelIngles("Acorde a lo enseñado en el colegio"))
                        .with(p -> p.setRindeExamen(false))
                        .with(p -> p.setPadresACargo(padresACargo))
                        .get();

        alumnoRepository.save(alumno1);
        alumnoRepository.save(alumno2);

        Usuario usuario = new Usuario();
        usuario.setActivo(true);
        usuario.setPassword("myPassword");
        usuario.setPerfil(Usuario.Perfil.ADMIN);
        usuario.setUsername("admin");
        usuario.setEmail("tower.admin@gmail.com");
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setActivo(true);

        usuario2.setPassword("myPassword");
        usuario2.setPerfil(Usuario.Perfil.PROFESOR);
        usuario2.setUsername("mayra.teacher");
        usuario2.setEmail("mayra.teacher@gmail.com");
        usuarioRepository.save(usuario2);

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

        Caja caja  = Builder.build(Caja.class)
                .with(p -> p.setUltimoMovimiento(movimiento))
                .with(p -> p.setValorActual(movimiento.getMonto()))
                .get();

        cajaRepository.save(caja);

        Contacto contacto4 = new Contacto("domicilio prueba","0239393","mayra.teacher@gmail.com");

        Profesor profesor = new Profesor();
        profesor.setCbuCvu("algo.algomas.mp");
        profesor.setValorHoraDiferenciado(false);
        profesor.setContacto(contacto4);
        profesor.setDni(2334343);
        profesor.setEdad(45);
        profesor.setExperienciaPrevia("instituto britanico");
        profesor.setNombreApellido("Mayra Gonzalez");
        profesor.setUsuario(usuario2);
        profesorRepository.save(profesor);

    }

    @Override
    public void run(String... args) throws Exception {
        initializeDb();
    }
}
