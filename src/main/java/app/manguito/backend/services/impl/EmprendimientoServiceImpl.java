package app.manguito.backend.services.impl;

import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.mappers.EmprendimientoMapper;
import app.manguito.backend.repositories.EmprendimientoRepository;
import app.manguito.backend.services.EmprendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmprendimientoServiceImpl implements EmprendimientoService {

    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Override
    public EmprendimientoDTO findEmprendimientoByUrl(String url) {
        return EmprendimientoMapper.INSTANCE.toDTO(emprendimientoRepository.findByUrl(url));
    }
}
