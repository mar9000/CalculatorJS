package CalculatorJS;

/*Generated by MPS */

import jetbrains.mps.smodel.language.LanguageRuntime;
import jetbrains.mps.smodel.adapter.ids.SLanguageId;
import java.util.UUID;
import java.util.Collection;
import jetbrains.mps.generator.runtime.TemplateModule;
import jetbrains.mps.generator.runtime.TemplateUtil;
import jetbrains.mps.smodel.runtime.ILanguageAspect;
import jetbrains.mps.openapi.editor.descriptor.EditorAspectDescriptor;
import CalculatorJS.editor.EditorAspectDescriptorImpl;
import jetbrains.mps.smodel.runtime.StructureAspectDescriptor;

public class Language extends LanguageRuntime {
  public static String MODULE_REF = "73f4da51-0e3e-448c-a68b-428ef5388ac7(CalculatorJS)";
  public Language() {
  }
  @Override
  public String getNamespace() {
    return "CalculatorJS";
  }

  @Override
  public int getVersion() {
    return 0;
  }

  public SLanguageId getId() {
    return new SLanguageId(UUID.fromString("73f4da51-0e3e-448c-a68b-428ef5388ac7"));
  }
  @Override
  protected String[] getExtendedLanguageIDs() {
    return new String[]{"org.mar9000.mps.ecmascript"};
  }
  @Override
  public Collection<TemplateModule> getGenerators() {
    return TemplateUtil.<TemplateModule>asCollection(TemplateUtil.createInterpretedGenerator(this, "2abf044e-2c9b-48b4-8c4b-79b7ac1df18d(CalculatorJS#2021265872597376002)"));
  }
  @Override
  protected <T extends ILanguageAspect> T createAspect(Class<T> aspectClass) {
    if (aspectClass == EditorAspectDescriptor.class) {
      return (T) new EditorAspectDescriptorImpl();
    }
    if (aspectClass == StructureAspectDescriptor.class) {
      return (T) new CalculatorJS.structure.StructureAspectDescriptor();
    }
    return super.createAspect(aspectClass);
  }
}