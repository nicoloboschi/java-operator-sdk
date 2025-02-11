package io.javaoperatorsdk.operator.processing.dependent.kubernetes;

import java.util.Set;

import io.javaoperatorsdk.operator.api.config.NamespaceChangeable;
import io.javaoperatorsdk.operator.api.reconciler.Constants;
import io.javaoperatorsdk.operator.api.reconciler.ResourceDiscriminator;
import io.javaoperatorsdk.operator.processing.event.source.filter.GenericFilter;
import io.javaoperatorsdk.operator.processing.event.source.filter.OnAddFilter;
import io.javaoperatorsdk.operator.processing.event.source.filter.OnDeleteFilter;
import io.javaoperatorsdk.operator.processing.event.source.filter.OnUpdateFilter;

import static io.javaoperatorsdk.operator.api.reconciler.Constants.NO_VALUE_SET;

public class KubernetesDependentResourceConfig<R> implements NamespaceChangeable {

  private Set<String> namespaces = Constants.SAME_AS_CONTROLLER_NAMESPACES_SET;
  private String labelSelector = NO_VALUE_SET;
  private boolean namespacesWereConfigured = false;
  private ResourceDiscriminator<R, ?> resourceDiscriminator;

  private OnAddFilter<R> onAddFilter;

  private OnUpdateFilter<R> onUpdateFilter;

  private OnDeleteFilter<R> onDeleteFilter;

  private GenericFilter<R> genericFilter;

  public KubernetesDependentResourceConfig() {}

  public KubernetesDependentResourceConfig(Set<String> namespaces, String labelSelector,
      boolean configuredNS, ResourceDiscriminator<R, ?> resourceDiscriminator,
      OnAddFilter<R> onAddFilter,
      OnUpdateFilter<R> onUpdateFilter,
      OnDeleteFilter<R> onDeleteFilter, GenericFilter<R> genericFilter) {
    this.namespaces = namespaces;
    this.labelSelector = labelSelector;
    this.namespacesWereConfigured = configuredNS;
    this.onAddFilter = onAddFilter;
    this.onUpdateFilter = onUpdateFilter;
    this.onDeleteFilter = onDeleteFilter;
    this.genericFilter = genericFilter;
    this.resourceDiscriminator = resourceDiscriminator;
  }

  public KubernetesDependentResourceConfig(Set<String> namespaces, String labelSelector) {
    this(namespaces, labelSelector, true, null, null, null,
        null, null);
  }

  public KubernetesDependentResourceConfig<R> setLabelSelector(String labelSelector) {
    this.labelSelector = labelSelector;
    return this;
  }

  public Set<String> namespaces() {
    return namespaces;
  }

  public String labelSelector() {
    return labelSelector;
  }

  public boolean wereNamespacesConfigured() {
    return namespacesWereConfigured;
  }

  @SuppressWarnings("rawtypes")
  public OnAddFilter onAddFilter() {
    return onAddFilter;
  }


  public OnUpdateFilter<R> onUpdateFilter() {
    return onUpdateFilter;
  }

  public OnDeleteFilter<R> onDeleteFilter() {
    return onDeleteFilter;
  }

  public GenericFilter<R> genericFilter() {
    return genericFilter;
  }

  @SuppressWarnings("rawtypes")
  public ResourceDiscriminator getResourceDiscriminator() {
    return resourceDiscriminator;
  }

  @Override
  public void changeNamespaces(Set<String> namespaces) {
    if (!wereNamespacesConfigured()) {
      this.namespacesWereConfigured = true;
      this.namespaces = namespaces;
    }
  }
}
