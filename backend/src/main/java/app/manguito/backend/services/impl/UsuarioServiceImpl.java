package app.manguito.backend.services.impl;

import app.manguito.backend.dto.NuevoUsuarioDTO;
import app.manguito.backend.entities.Rol;
import app.manguito.backend.entities.Usuario;
import app.manguito.backend.exception.AppException;
import app.manguito.backend.mappers.UsuarioMapper;
import app.manguito.backend.repositories.RolRepository;
import app.manguito.backend.repositories.UsuarioRepository;
import app.manguito.backend.services.EmprendimientoService;
import app.manguito.backend.services.UsuarioService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmprendimientoService emprendimientoService;

    public UsuarioServiceImpl(UsuarioRepository userRepository, UsuarioMapper usuarioMapper, RolRepository roleRepository, PasswordEncoder passwordEncoder, EmprendimientoService emprendimientoService) {
        this.userRepository = userRepository;
        this.usuarioMapper = usuarioMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emprendimientoService = emprendimientoService;
    }


    @Override
    public void saveUsuario(NuevoUsuarioDTO usuarioDTO) {
        try {
            Usuario user = usuarioMapper.toNewUser(usuarioDTO);
            user.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

            Rol role = roleRepository.findByNombre("ROLE_USER");
            if (role == null) {
                role = checkRoleExist();
            }
            user.setRol(role);
            userRepository.save(user);
            user.setEmprendimiento(emprendimientoService.saveEmprendimiento(usuarioDTO.getEmprendimiento()));
            userRepository.save(user);
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al crear el usuario", pe);
        }
    }

    @Override
    public Usuario findUsuarioByMail(String mail) {
        try {
            return userRepository.findByMail(mail)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Usuario con email '" + mail + "' no encontrado")
                    );
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al recuperar el usuario con mail: " + mail, pe);
        }
    }

    @Override
    public Boolean existsUserOrEmprendimientoUrl(NuevoUsuarioDTO dto) {
        try {
            return userRepository.existsByMailOrEmprendimiento_Url(dto.getMail(), dto.getEmprendimiento().getUrl());
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al verificar el usuario", pe);
        }
    }

    private Rol checkRoleExist(){
        Rol role = new Rol();
        role.setNombre("ROLE_USER");
        return roleRepository.save(role);
    }
}
