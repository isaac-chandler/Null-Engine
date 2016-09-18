#version 150
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;

void main() {
	outColor = textureOffset(colors, texCoord, ivec2(-5, 0)) * 0.0093;
	outColor += textureOffset(colors, texCoord, ivec2(-4, 0)) * 0.028002;
	outColor += textureOffset(colors, texCoord, ivec2(-3, 0)) * 0.065984;
	outColor += textureOffset(colors, texCoord, ivec2(-2, 0)) * 0.121703;
	outColor += textureOffset(colors, texCoord, ivec2(-1, 0)) * 0.175713;
	outColor += texture(colors, texCoord) * 0.198596;
	outColor += textureOffset(colors, texCoord, ivec2(1, 0)) * 0.175713;
	outColor += textureOffset(colors, texCoord, ivec2(2, 0)) * 0.121703;
	outColor += textureOffset(colors, texCoord, ivec2(3, 0)) * 0.065984;
	outColor += textureOffset(colors, texCoord, ivec2(4, 0)) * 0.028002;
	outColor += textureOffset(colors, texCoord, ivec2(5, 0)) * 0.0093;
}
