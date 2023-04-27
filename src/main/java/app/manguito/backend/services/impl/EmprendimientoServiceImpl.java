package app.manguito.backend.services.impl;

import app.manguito.backend.EstadoPago;
import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.dto.RedSocialEmprendimientoDTO;
import app.manguito.backend.dto.UpdateEmprendimientoDTO;
import app.manguito.backend.entities.*;
import app.manguito.backend.exception.AppException;
import app.manguito.backend.mappers.EmprendimientoMapper;
import app.manguito.backend.mappers.RedSocialEmprendimientoMapper;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.EmprendimientoRepository;
import app.manguito.backend.repositories.SuscripcionRepository;
import app.manguito.backend.repositories.TransaccionManguitoRepository;
import app.manguito.backend.repositories.UsuarioRepository;
import app.manguito.backend.services.EmprendimientoService;
import app.manguito.backend.services.R2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
public class EmprendimientoServiceImpl implements EmprendimientoService {

    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Autowired
    private TransaccionManguitoRepository manguitoRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprendimientoMapper emprendimientoMapper;

    @Autowired
    private TransaccionMapper transaccionMapper;

    @Autowired
    private RedSocialEmprendimientoMapper redSocialEmprendimientoMapper;

    @Autowired
    private R2Service r2Service;

    @Override
    public List<EmprendimientoDTO> findEmprendimientos() {
        try {
            return emprendimientoMapper.toDTOList(emprendimientoRepository.findAll());
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al recuperar emprendimientos", pe);
        }
    }

    @Override
    public EmprendimientoDTO findEmprendimientoByUrl(String url) {
        try {
            return emprendimientoMapper.toDTO(emprendimientoRepository.findByUrl(url));
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al recuperar el emprendimiento con url: " + url, pe);
        }
    }

    @Override
    public EmprendimientoDTO findEmprendimientoByMail(String mail) {
        try {
            Usuario usuario = usuarioRepository.findByMail(mail).orElse(null);
            if (usuario == null) return null;
            return emprendimientoMapper.toDTO(usuario.getEmprendimiento());
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al recuperar el emprendimiento", pe);
        }
    }

    @Override
    public Emprendimiento saveEmprendimiento(EmprendimientoDTO dto) {
        try {
            return emprendimientoRepository.save(emprendimientoMapper.toEntity(dto));
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al intentar guardar el emprendimiento", pe);
        }
    }

    @Override
    public EmprendimientoDTO updateEmprendimiento(UpdateEmprendimientoDTO dto) {
        try {
            Emprendimiento emprendimiento = emprendimientoRepository.findByUrl(dto.getUrl());
            if (emprendimiento == null) return null;
            emprendimiento = emprendimientoMapper.update(emprendimiento, dto);
            if (dto.getNewBanner() != null) {
                emprendimiento.setBanner(r2Service.saveImage(dto.getNewBanner()));
            }
            if (dto.getNewImagenPerfil() != null) {
                emprendimiento.setImagenPerfil(r2Service.saveImage(dto.getNewImagenPerfil()));
            }
            updateRedesSociales(emprendimiento, dto.getRedesSociales());
            emprendimiento = emprendimientoRepository.save(emprendimiento);
            return emprendimientoMapper.toDTO(emprendimiento);
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al intentar actualizar los datos del emprendimiento", pe);
        }
    }

    private void updateRedesSociales(Emprendimiento emprendimiento, List<RedSocialEmprendimientoDTO> redesSociales) {
        if (redesSociales == null) return;
        List<RedSocialEmprendimiento> redesSocialesEntities = redSocialEmprendimientoMapper.toEntityList(redesSociales);
        redesSocialesEntities.parallelStream().forEach(rs -> {
           Optional<RedSocialEmprendimiento> redsocial = emprendimiento.getRedesSociales().stream()
                   .filter(rse -> rse.getRedSocial().getNombre().equals(rs.getRedSocial().getNombre())).findAny();
           if (redsocial.isPresent()) {
               if (rs.containsUrl())
                   redsocial.get().setUrl(rs.getUrl());
               else
                   emprendimiento.getRedesSociales().remove(redsocial.get());
           } else {
               if (rs.containsUrl())
                   emprendimiento.getRedesSociales().add(rs);
           }
        });
    }

    @Override
    public DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl) {
        try {
            if (!emprendimientoRepository.existsByUrl(emprendimientoUrl)) return null;

            List<TransaccionManguito> manguitos = manguitoRepository.findAllByDestinatario_UrlAndEstado(emprendimientoUrl, EstadoPago.APROBADO.getCodigo());
            List<Suscripcion> suscripciones = suscripcionRepository.findAllByDestinatario_UrlAndEstado(emprendimientoUrl, EstadoPago.APROBADO.getCodigo());
            DonacionesDTO dto = new DonacionesDTO();
            dto.setManguitos(transaccionMapper.toManguitoDTOList(manguitos));
            dto.setSuscripciones(transaccionMapper.toSuscripcionDTOList(suscripciones));
            return dto;
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al recuperar las donaciones", pe);
        }
    }
}
