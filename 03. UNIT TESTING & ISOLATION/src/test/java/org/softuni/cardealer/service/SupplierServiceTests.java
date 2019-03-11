package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTests {

    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;

    private SupplierService supplierService;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);


//        Correct way is here to seed some data in the database
    }

    @Test
    public void supplierService_saveSupplierWithCorrectValues_ReturnsCorrected(){
        SupplierServiceModel toBeSaved = new SupplierServiceModel();
        toBeSaved.setName("Pesho");
        toBeSaved.setImporter(true);

        SupplierServiceModel actual = supplierService.saveSupplier(toBeSaved);
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        Assert.assertEquals("Ids do not match!", expected.getId(), actual.getId());
        Assert.assertEquals("Names do not match!", expected.getName(), actual.getName());
        Assert.assertEquals("IsImporter do not match!", expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_saveSupplierWithNUllValues_ThrowsException(){
        SupplierServiceModel toBeSaved = new SupplierServiceModel();
        toBeSaved.setName(null);

        supplierService.saveSupplier(toBeSaved);
    }

    @Test
    public void supplierService_editSupplierWithCorrectValues_ReturnsCorrect(){
        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);

        SupplierServiceModel toBeEdited = new SupplierServiceModel();
        toBeEdited.setId(supplier.getId());
        toBeEdited.setName("Gosho");
        toBeEdited.setImporter(false);

        SupplierServiceModel actual = supplierService.editSupplier(toBeEdited);
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        Assert.assertEquals("Ids do not match!", expected.getId(), actual.getId());
        Assert.assertEquals("Names do not match!", expected.getName(), actual.getName());
        Assert.assertEquals("IsImporter do not match!", expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_editSupplierWithNullValues_ThrowsException(){
        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);

        SupplierServiceModel toBeEdited = new SupplierServiceModel();
        toBeEdited.setId(supplier.getId());
        toBeEdited.setName(null);

        supplierService.editSupplier(toBeEdited);
    }

    @Test
    public void supplierService_deleteSupplierWithValidId_ReturnsCorrect(){
        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);
        supplier = this.supplierRepository.saveAndFlush(supplier);

        supplierService.deleteSupplier(supplier.getId());

        long expected = 0;
        long actual = this.supplierRepository.findAll().size();

        Assert.assertEquals(expected, actual);
    }
}
