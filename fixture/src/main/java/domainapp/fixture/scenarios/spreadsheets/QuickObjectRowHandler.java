package domainapp.fixture.scenarios.spreadsheets;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.excel.dom.ExcelFixture;
import org.isisaddons.module.excel.dom.ExcelFixtureRowHandler;

import domainapp.dom.quick.QuickObject;
import domainapp.dom.quick.QuickObjectRepository;
import lombok.Getter;
import lombok.Setter;

public class QuickObjectRowHandler implements ExcelFixtureRowHandler {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Integer anInteger;

    @Getter @Setter
    private LocalDate localDate;

    @Getter @Setter
    private Boolean aBoolean;

    @Override
    public List<Object> handleRow(
            final FixtureScript.ExecutionContext executionContext,
            final ExcelFixture excelFixture,
            final Object previousRow) {
        final QuickObject quickObject = quickObjectRepository.findOrCreate(name);
        quickObject.setBoolean(getABoolean());
        quickObject.setInteger(getAnInteger());
        quickObject.setLocalDate(getLocalDate());
        executionContext.addResult(excelFixture, this);
        return Collections.singletonList(quickObject);
    }

    @Inject
    QuickObjectRepository quickObjectRepository;

}
