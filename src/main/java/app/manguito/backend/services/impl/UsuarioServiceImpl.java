package app.manguito.backend.services.impl;

import app.manguito.backend.dto.NuevoUsuarioDTO;
import app.manguito.backend.entities.Rol;
import app.manguito.backend.entities.Usuario;
import app.manguito.backend.mappers.UsuarioMapper;
import app.manguito.backend.repositories.RolRepository;
import app.manguito.backend.repositories.UsuarioRepository;
import app.manguito.backend.services.EmprendimientoService;
import app.manguito.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private RolRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmprendimientoService emprendimientoService;


    @Override
    public void saveUsuario(NuevoUsuarioDTO usuarioDTO) {
        Usuario user = usuarioMapper.toNewUser(usuarioDTO);
        user.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        Rol role = roleRepository.findByNombre("ROLE_USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRol(role);
        userRepository.save(user);
        user.setEmprendimiento(emprendimientoService.saveEmprendimiento(usuarioDTO.getEmprendimiento()));
        userRepository.save(user);
    }

    @Override
    public Usuario findUsuarioByMail(String mail) {
        return userRepository.findByMail(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario con email '" + mail + "' no encontrado")
                );
    }

    @Override
    public Boolean existsUserOrEmprendimientoUrl(NuevoUsuarioDTO dto) {
        return userRepository.existsByMailOrEmprendimiento_Url(dto.getMail(), dto.getEmprendimiento().getUrl());
    }

    private Rol checkRoleExist(){
        Rol role = new Rol();
        role.setNombre("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
