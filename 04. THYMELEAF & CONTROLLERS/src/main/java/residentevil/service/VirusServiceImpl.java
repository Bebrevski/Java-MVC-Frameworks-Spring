package residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.Virus;
import residentevil.domain.model.service.VirusServiceModel;
import residentevil.repository.VirusRepository;

@Service
public class VirusServiceImpl implements VirusService {

    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel addVirus(VirusServiceModel virusServiceModel) {

        Virus entity = this.modelMapper.map(virusServiceModel, Virus.class);

        return this.modelMapper.map(this.virusRepository.saveAndFlush(entity), VirusServiceModel.class);
    }
}
