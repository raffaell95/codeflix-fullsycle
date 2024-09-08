from rest_framework import serializers


class GenreOutputSerializer(serializers.Serializer):
    id = serializers.UUIDField()
    name = serializers.CharField(max_length=255)
    is_active = serializers.BooleanField()
    categories = serializers.ListField(child=serializers.UUIDField())

class ListGenreOutputSerializer(serializers.Serializer):
    data = GenreOutputSerializer(many=True)

class SetField(serializers.ListField):
    def to_internal_value(self, data):
        return set(super().to_internal_value(data))

class CreateGenreInputSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=255)
    categories = SetField(child=serializers.UUIDField(), required=False)
    is_active = serializers.BooleanField(default=True)

class CreateGenreOutputSerializer(serializers.Serializer):
    id = serializers.UUIDField()

class DeleteGenreInputSerializer(serializers.Serializer):
    id = serializers.UUIDField()

class UpdateGenreInputSerializer(serializers.Serializer):
    id = serializers.UUIDField(required=True)
    name = serializers.CharField(required=True)
    is_active = serializers.BooleanField(required=True)
    categories = SetField(child=serializers.UUIDField(), required=True, allow_empty=True)