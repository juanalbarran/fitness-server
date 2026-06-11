package com.jarsolutions.fitness.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.web.bind.annotation.RestController;

// Scoped to bounded contexts that implement hexagonal architecture.
// The 'user' package is legacy code pending migration and is excluded.
@AnalyzeClasses(
    packages = {"com.jarsolutions.fitness.musclegroup", "com.jarsolutions.fitness.muscle"},
    importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

  // ── Layer isolation ──────────────────────────────────────────────────────────

  @ArchTest
  static final ArchRule domainMustNotDependOnInfrastructure =
      noClasses()
          .that()
          .resideInAPackage("..domain..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..infrastructure..");

  @ArchTest
  static final ArchRule domainMustNotDependOnSpringOrJpa =
      noClasses()
          .that()
          .resideInAPackage("..domain..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("org.springframework..", "jakarta.persistence..");

  @ArchTest
  static final ArchRule applicationMustNotDependOnInfrastructure =
      noClasses()
          .that()
          .resideInAPackage("..application..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..infrastructure..");

  // ── Infrastructure constraints ───────────────────────────────────────────────

  @ArchTest
  static final ArchRule jpaEntitiesMustResideInPersistencePackage =
      classes()
          .that()
          .areAnnotatedWith(Entity.class)
          .should()
          .resideInAPackage("..infrastructure.out.persistence..");

  @ArchTest
  static final ArchRule jpaRepositoriesMustResideInPersistencePackage =
      classes()
          .that()
          .haveSimpleNameEndingWith("JpaRepository")
          .should()
          .resideInAPackage("..infrastructure.out.persistence..");

  @ArchTest
  static final ArchRule inboundAdaptersMustNotBypassPortsToCallServices =
      noClasses()
          .that()
          .resideInAPackage("..infrastructure.in..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..application.service..");

  // ── Naming and placement conventions ─────────────────────────────────────────

  @ArchTest
  static final ArchRule restControllersMustResideInControllerPackage =
      classes()
          .that()
          .areAnnotatedWith(RestController.class)
          .should()
          .resideInAPackage("..infrastructure.in.web.controller..");

  @ArchTest
  static final ArchRule useCasesMustResideInPortIn =
      classes()
          .that()
          .haveSimpleNameEndingWith("UseCase")
          .should()
          .resideInAPackage("..application.port.in..");

  @ArchTest
  static final ArchRule adaptersMustResideInInfrastructure =
      classes()
          .that()
          .haveSimpleNameEndingWith("Adapter")
          .should()
          .resideInAPackage("..infrastructure..");

  // ── Domain integrity ─────────────────────────────────────────────────────────

  @ArchTest
  static final ArchRule domainAndApplicationPortsMustBeInterfaces =
      classes()
          .that()
          .resideInAnyPackage("..domain.port..", "..application.port.in..")
          .should()
          .beInterfaces();
}
